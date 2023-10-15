package ru.sbrf.rnd.console.profiling;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class ProfilingApplication {


    public static void main(String[] args) {

        Profiling profiling = new Profiling(args);

        List<AttitudeDto> attitudeDtos = profiling.getAttitudeDto();
        Map<String, PdmJsonFileDto> pdmFile = profiling.getPdmFile();
        profiling.createReportFile(attitudeDtos, pdmFile);
    }

}
