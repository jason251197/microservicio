package project.jason.store.serviceproduct.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErroMensaje {
    private String code;
    private List<Map<String, String>> mensajes;
}
