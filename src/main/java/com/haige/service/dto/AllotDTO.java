package com.haige.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author Archie
 * @date 2019/11/20 23:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllotDTO {
    /**
     * 服务单id
     */
    private Integer serviceId;
    /**
     * 指派的服务人员
     */
    private Integer userId;

}
