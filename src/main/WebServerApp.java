package main;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import model.PermissionRequest;
import service.PermissionService;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.List;

public class WebServerApp {
    private static final PermissionService service = new PermissionService();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        
        server.createContext("/", new StaticHandler());
        server.createContext("/api/requests", new GetRequestsHandler());
        server.createContext("/api/apply", new PostApplyHandler());
        
        server.setExecutor(null);
        server.start();
        System.out.println("=================================================");
        System.out.println("Java Native Web Server Successfully Started!");
        System.out.println("-> http://localhost:" + port);
        System.out.println("=================================================");
    }

    static class StaticHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            File file = new File("public/index.html");
            if (!file.exists()) {
                String response = "<h1>Error: Cannot find public/index.html!</h1>";
                t.sendResponseHeaders(404, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
                return;
            }
            byte[] bytes = Files.readAllBytes(file.toPath());
            t.getResponseHeaders().add("Content-Type", "text/html");
            t.sendResponseHeaders(200, bytes.length);
            OutputStream os = t.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }

    static class GetRequestsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            List<PermissionRequest> list = service.getFullHistory();
            StringBuilder json = new StringBuilder("[");
            for (int i=0; i<list.size(); i++) {
                PermissionRequest r = list.get(i);
                json.append(String.format("{\"id\":%d,\"employeeId\":%d,\"employeeName\":\"%s\",\"reason\":\"%s\",\"status\":\"%s\",\"decisionReason\":\"%s\"}",
                    r.getId(), r.getEmployeeId(), escapeJson(r.getEmployeeName()), escapeJson(r.getReason()), r.getStatus(), escapeJson(r.getDecisionReason())));
                if(i < list.size() - 1) json.append(",");
            }
            json.append("]");

            t.getResponseHeaders().add("Content-Type", "application/json");
            byte[] response = json.toString().getBytes();
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            os.write(response);
            os.close();
        }
    }

    static class PostApplyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {            
            InputStream is = t.getRequestBody();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) sb.append(line);
            String jsonStr = sb.toString();

            int empId = extractInt(jsonStr, "employeeId");
            String empName = extractString(jsonStr, "employeeName");
            String reason = extractString(jsonStr, "reason");
            String reqDateStr = extractString(jsonStr, "requestDate");
            int numDays = extractInt(jsonStr, "numberOfDays");
            String leaveType = extractString(jsonStr, "leaveType");
            int pastCount = extractInt(jsonStr, "pastLeaveCount");

            try {
                PermissionRequest request = new PermissionRequest(empId, empName, reason, sdf.parse(reqDateStr), numDays, leaveType, pastCount);
                PermissionRequest processed = service.processRequest(request);
                
                String responseJson = String.format("{\"status\":\"%s\",\"decisionReason\":\"%s\"}", processed.getStatus(), escapeJson(processed.getDecisionReason()));
                
                t.getResponseHeaders().add("Content-Type", "application/json");
                byte[] response = responseJson.getBytes();
                t.sendResponseHeaders(200, response.length);
                OutputStream os = t.getResponseBody();
                os.write(response);
                os.close();

            } catch (Exception e) {
                e.printStackTrace();
                String err = "{\"error\":\"" + e.getMessage() + "\"}";
                t.sendResponseHeaders(500, err.length());
                OutputStream os = t.getResponseBody();
                os.write(err.getBytes());
                os.close();
            }
        }
        
        private String extractString(String json, String key) {
            String search = "\"" + key + "\":\"";
            int start = json.indexOf(search);
            if (start == -1) return "unknown";
            start += search.length();
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        }
        
        private int extractInt(String json, String key) {
            String search = "\"" + key + "\":";
            int start = json.indexOf(search);
            if(start == -1) {
                String strSearch = "\"" + key + "\":\"";
                int start2 = json.indexOf(strSearch);
                if (start2 == -1) return 0;
                start2 += strSearch.length();
                int end2 = json.indexOf("\"", start2);
                return Integer.parseInt(json.substring(start2, end2));
            }
            start += search.length();
            int end = json.indexOf(",", start);
            int endBracket = json.indexOf("}", start);
            if (end == -1 || (endBracket != -1 && endBracket < end)) end = endBracket;
            try { return Integer.parseInt(json.substring(start, end).trim()); } 
            catch(Exception e) { return 0; }
        }
    }

    private static String escapeJson(String str) {
        if(str == null) return "";
        return str.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }
}
