
package ru.sbrf.rnd.console.profiling;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PdmJsonFileDto {

    @JsonProperty("jsonSchemaVersion")
    private String jsonSchemaVersion;
    @JsonProperty("phdmSign")
    private Boolean phdmSign;
    @JsonProperty("smId")
    private String smId;
    
}
