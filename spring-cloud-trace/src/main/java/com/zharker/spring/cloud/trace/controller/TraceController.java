package com.zharker.spring.cloud.trace.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
public class TraceController {

    @Value("${trace.id}")
    private String traceId;

    @Value("${trace.next}")
    private String nextTraces;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/tracetrace", method = RequestMethod.GET)
    public String trace(@RequestParam(value = "previous",required = false) String previousTrace, HttpServletRequest request) {
        log.info("===<call trace-{}, TraceId={}, SpanId={}>===",traceId,
                request.getHeader("X-B3-TraceId"), request.getHeader("X-B3-SpanId"));

        try {
            long sleepMilliseconds = Math.round(Math.random()*1000);
            log.info("trace-{} sleep {}ms to mockito handle",traceId,sleepMilliseconds);
            Thread.sleep(sleepMilliseconds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!StringUtils.isEmpty(nextTraces) && nextTraces.split(",").length>0){
            if(StringUtils.isEmpty(previousTrace)){
                previousTrace = "start";
            }
            String result = previousTrace+"=>"+Arrays.asList(nextTraces.split(",")).parallelStream().map(nextTrace->restTemplate.getForEntity("http://TRACE-"+nextTrace+"/tracetrace?previous="+traceId, String.class).getBody())
                    .collect(Collectors.joining("<br/>"));
            log.info("call next traces result: {}",result);
            return result;
        }else{
            return previousTrace+"=>"+traceId+"=>end";
        }
    }
}
