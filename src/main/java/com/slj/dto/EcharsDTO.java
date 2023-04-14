package com.slj.dto;

import com.slj.domain.Echars;
import lombok.Data;

import java.util.List;
import java.util.Map;

/*
 * @author Shu
 * @Data 2022-11-28
 */
@Data
public class EcharsDTO {
    private Map ordersCount;
    private List<Echars> list;
}
