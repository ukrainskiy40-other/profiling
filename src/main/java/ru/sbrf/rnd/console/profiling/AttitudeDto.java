package ru.sbrf.rnd.console.profiling;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AttitudeDto {

    private String asSmId;
    private String prodSmId;
    private String prodFileName;
    private String psiSmId;
    private String psiFileName;

    @Override
    public String toString() {
        return asSmId + Profiling.CSV_SPLIT_BY
                + prodSmId + Profiling.CSV_SPLIT_BY
                + prodFileName + Profiling.CSV_SPLIT_BY
                + psiSmId + Profiling.CSV_SPLIT_BY
                + psiFileName + "\n";
    }
}
