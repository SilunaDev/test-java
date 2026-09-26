package com.vulnerable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class VulnerableController {

    // Vulnerable Log4j instance
    private static final Logger logger = LogManager.getLogger(VulnerableController.class);

    @GetMapping("/")
    public String index(@RequestHeader(value = "User-Agent", defaultValue = "Unknown") String userAgent) {
        
        // This line triggers Log4Shell! The attacker can put a JNDI payload in the User-Agent header.
        logger.error("Request received from User-Agent: " + userAgent);
        
        return "Hello World! This application is extremely vulnerable to Log4Shell and Spring4Shell!";
    }

    @GetMapping("/parse")
    public String parseData(String json) {
        try {
            // Vulnerable Jackson parsing
            ObjectMapper mapper = new ObjectMapper();
            mapper.enableDefaultTyping(); 
            mapper.readValue(json, Object.class);
            return "Parsed successfully!";
        } catch (Exception e) {
            return "Parsing error!";
        }
    }
}
