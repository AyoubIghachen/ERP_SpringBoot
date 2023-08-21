package com.allianceever.projectERP.model.dto;

        import jakarta.persistence.Column;
        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesignationDto {
    private Long designationID;
    private String designationName;
    private String departmentName;
}
