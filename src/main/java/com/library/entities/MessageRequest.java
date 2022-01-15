package com.library.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class MessageRequest implements Serializable {
    private OutboundSMSMessageRequest outboundSMSMessageRequest;
}
