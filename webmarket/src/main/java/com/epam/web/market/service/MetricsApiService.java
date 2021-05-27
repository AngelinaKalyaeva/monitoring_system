package com.epam.web.market.service;

import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.apache.http.client.fluent.Request;

import java.time.LocalTime;
import java.time.ZoneId;

@Component
public class MetricsApiService {
    @SneakyThrows
    public void sendUrlAttendenceMetric(String url) {
        sendUrlErrorCount(url);
        LocalTime target = LocalTime.now(ZoneId.of("Europe/Moscow"));
        boolean targetInZone = (
                target.isAfter( LocalTime.parse( "21:42:00" ) )
                        &&
                        target.isBefore( LocalTime.parse( "21:52:10" ) )
        );

        boolean targetInZone2 = (
                target.isAfter( LocalTime.parse( "21:45:00" ) )
                        &&
                        target.isBefore( LocalTime.parse( "21:48:10" ) )
        );

        if (!url.equals("/")) {
            return;
        }

        if ((Math.random() <= 0.5) && targetInZone) {
            return;
        }

        if ((Math.random() <= 0.5) && targetInZone2) {
            return;
        }
        System.out.println("sendUrlAttendenceMetric --> " + url);

        Request request = Request.Post("http://localhost:8080/graphql");
        String body = String.format("{\"query\":\"mutation { writeAttendancy(serviceId: \\\"10\\\", url: \\\"%s\\\", count: 1) { service { datetime analytics { attendance { url count } } } } }\"}", url);
        request.bodyString(body,ContentType.APPLICATION_JSON);
        request.setHeader("Accept-Encoding", "gzip, deflate, br");
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        request.setHeader("Connection", "keep-alive");

        HttpResponse httpResponse = request.execute().returnResponse();
        System.out.println(httpResponse.getStatusLine());
        if (httpResponse.getEntity() != null) {
            String html = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(html);
        }
    }

    @SneakyThrows
    public void sendDynamicSaleMetric(int byedAmount) {
        if ((Math.random() <= 0.05)) {
            sendPaymentSystemStatus("500");
            return;
        } else {
            sendPaymentSystemStatus("200");
        }

        System.out.println("sendDynamicSaleMetric");
        Request request = Request.Post("http://localhost:8080/graphql");
        String body = String.format("{\"query\":\"mutation { writeSaleDynamic(serviceId: \\\"10\\\", saleCount: 1, returnCount: 0,  cost: %s) { service { datetime analytics { dynamic { sale { saleCount } } } } } }\"}", byedAmount);
        request.bodyString(body,ContentType.APPLICATION_JSON);
        request.setHeader("Accept-Encoding", "gzip, deflate, br");
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        request.setHeader("Connection", "keep-alive");

        HttpResponse httpResponse = request.execute().returnResponse();
        System.out.println(httpResponse.getStatusLine());
        if (httpResponse.getEntity() != null) {
            String html = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(html);
        }
    }

    @SneakyThrows
    public void sendProductDynamic(int productId, int salesCount) {
        System.out.println("sendProductDynamic");
        Request request = Request.Post("http://localhost:8080/graphql");
        String body = String.format("{\"query\":\"mutation { writeProductDynamic(serviceId: \\\"10\\\", productId: \\\"%s\\\", salesCount: %s) { service { datetime } } }\"}", productId, salesCount);
        request.bodyString(body,ContentType.APPLICATION_JSON);
        request.setHeader("Accept-Encoding", "gzip, deflate, br");
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        request.setHeader("Connection", "keep-alive");

        HttpResponse httpResponse = request.execute().returnResponse();
        System.out.println(httpResponse.getStatusLine());
        if (httpResponse.getEntity() != null) {
            String html = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(html);
        }
    }

    @SneakyThrows
    public void sendPaymentSystemStatus(String code) {

        LocalTime target = LocalTime.now(ZoneId.of("Europe/Moscow"));
        boolean targetInZone = (
                target.isAfter( LocalTime.parse( "10:00:00" ) )
                        &&
                        target.isBefore( LocalTime.parse( "10:35:00" ) )
        );

        boolean targetInZone2 = (
                target.isAfter( LocalTime.parse( "10:05:00" ) )
                        &&
                        target.isBefore( LocalTime.parse( "10:25:10" ) )
        );

        code = "200";

        if ((Math.random() <= 0.3) && targetInZone) {
            code = "500";
        }

        if ((Math.random() <= 0.99) && targetInZone2) {
            code = "500";
        }

        System.out.println("sendPaymentSystem");
        Request request = Request.Post("http://localhost:8080/graphql");
        String body = String.format("{\"query\":\"mutation { writePaymentSystemMetric(serviceId: \\\"10\\\", code: \\\"%s\\\", type: \\\"visa\\\") { service { id efficiency { payment { code } } } } }\"}", code);
        request.bodyString(body,ContentType.APPLICATION_JSON);
        request.setHeader("Accept-Encoding", "gzip, deflate, br");
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        request.setHeader("Connection", "keep-alive");

        HttpResponse httpResponse = request.execute().returnResponse();
        System.out.println(httpResponse.getStatusLine());
        if (httpResponse.getEntity() != null) {
            String html = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(html);
        }
    }

    @SneakyThrows
    public void sendUrlErrorCount(String url) {
        String code = "200";
        if (Math.random() <= 0.04) {
            code = "500";
        }

        System.out.println("sendUrlErrorCount --> " + url);

        Request request = Request.Post("http://localhost:8080/graphql");
        String body = String.format("{\"query\":\"mutation { writeSlaError(serviceId: \\\"10\\\", url: \\\"%s\\\", code: \\\"%s\\\", ) { service { id efficiency { sla { url error { code } } } } } }\"}", url, code);
        request.bodyString(body,ContentType.APPLICATION_JSON);
        request.setHeader("Accept-Encoding", "gzip, deflate, br");
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        request.setHeader("Connection", "keep-alive");

        HttpResponse httpResponse = request.execute().returnResponse();
        System.out.println(httpResponse.getStatusLine());
        if (httpResponse.getEntity() != null) {
            String html = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(html);
        }
    }

    @SneakyThrows
    public void sendQueryErrorCount(String query) {
        String code = "200";
        if (Math.random() <= 0.04) {
            code = "500";
        }

        System.out.println("sendQueryErrorCount --> " + query);

        Request request = Request.Post("http://localhost:8080/graphql");
        String body = String.format("{\"query\":\"mutation { writeDatabaseError(serviceId: \\\"10\\\", query: \\\"%s\\\", code: \\\"%s\\\", ) { service { id efficiency { database { query error { code } } } } } }\"}", query, code);
        request.bodyString(body,ContentType.APPLICATION_JSON);
        request.setHeader("Accept-Encoding", "gzip, deflate, br");
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        request.setHeader("Connection", "keep-alive");

        HttpResponse httpResponse = request.execute().returnResponse();
        System.out.println(httpResponse.getStatusLine());
        if (httpResponse.getEntity() != null) {
            String html = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(html);
        }
    }

    @SneakyThrows
    public void sendSlaUrlTime(String url, long time) {
        System.out.println("sendSlaUrlTime --> " + url);

        Request request = Request.Post("http://localhost:8080/graphql");
        String body = String.format("{\"query\":\"mutation { writeSlaTiming(serviceId: \\\"10\\\", url: \\\"%s\\\", time: %s, slaTime: 300) { service { id efficiency { sla { url timing { time slaTime } } } } } }\"}", url, time);
        request.bodyString(body,ContentType.APPLICATION_JSON);
        request.setHeader("Accept-Encoding", "gzip, deflate, br");
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        request.setHeader("Connection", "keep-alive");

        HttpResponse httpResponse = request.execute().returnResponse();
        System.out.println(httpResponse.getStatusLine());
        if (httpResponse.getEntity() != null) {
            String html = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(html);
        }
    }

    @SneakyThrows
    public void sendSlaDatabaseTime(String query, long time) {
        System.out.println("sendSlaDatabaseTime --> " + query);

        Request request = Request.Post("http://localhost:8080/graphql");
        String body = String.format("{\"query\":\"mutation { writeDatabaseTiming(serviceId: \\\"10\\\", query: \\\"%s\\\", time: %s, slaTime: 50) { service { id efficiency { database { query timing { time slaTime } } } } } }\"}", query, time);
        request.bodyString(body,ContentType.APPLICATION_JSON);
        request.setHeader("Accept-Encoding", "gzip, deflate, br");
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        request.setHeader("Connection", "keep-alive");

        HttpResponse httpResponse = request.execute().returnResponse();
        System.out.println(httpResponse.getStatusLine());
        if (httpResponse.getEntity() != null) {
            String html = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(html);
        }
    }
}
