package ru.sbrf.rnd.console.profiling;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class Profiling {

    public static final String CSV_SPLIT_BY = ";";

    private final String dirName;
    private final String attitudeFileName;
    private final String logFileName;


    public Profiling(String... args) {
        if (args.length != 3) {
            log.error("\nNo args. \nBe sure to specify three parameters. \nDirectory with files, csv file and result file separated by a space.");
            System.exit(0);
        }
        this.dirName = args[0];
        this.attitudeFileName = args[1];
        this.logFileName = args[2];
    }

    public void createReportFile(List<AttitudeDto> attitudeDtos, Map<String, PdmJsonFileDto> pdmFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName + ".csv", false))) {
            Set<AttitudeDto> report = new HashSet<>();
            for (AttitudeDto attitudeDto : attitudeDtos) {
                pdmFile.entrySet().stream().filter(entry -> entry.getValue().getSmId().equals(attitudeDto.getProdSmId())).findFirst()
                        .ifPresent(entiry -> attitudeDto.setProdFileName(entiry.getKey()));
                pdmFile.entrySet().stream().filter(entry -> entry.getValue().getSmId().equals(attitudeDto.getPsiSmId())).findFirst()
                        .ifPresent(entiry -> attitudeDto.setPsiFileName(entiry.getKey()));
                report.add(attitudeDto);
            }
            report.forEach(attitude -> {
                try {
                    writer.write(attitude.toString());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public List<AttitudeDto> getAttitudeDto() {
        List<AttitudeDto> attitudeDtos = new ArrayList<>();
        File attitudeFile = new File(attitudeFileName);
        try (BufferedReader attitudeBR = new BufferedReader(new FileReader(attitudeFile))) {
            String line;
            while ((line = attitudeBR.readLine()) != null) {
                String[] smIdAttitude = line.split(CSV_SPLIT_BY);
                if (smIdAttitude[0].startsWith("CI")) {
                    attitudeDtos.add(AttitudeDto.builder().asSmId(smIdAttitude[0]).prodSmId(smIdAttitude[1]).psiSmId(smIdAttitude[2]).build());
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return attitudeDtos;
    }

    public Map<String, PdmJsonFileDto> getPdmFile() {
        Map<String, PdmJsonFileDto> pdmFile = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File folder = new File(dirName);
            File[] listOfFiles = folder.listFiles();
            if (listOfFiles == null || listOfFiles.length < 1) {
                log.error("Directory is empty");
                System.exit(-1);
            }
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".json")) {
                    PdmJsonFileDto jsonFile = objectMapper.readValue(file, PdmJsonFileDto.class);
                    pdmFile.put(file.getName(), jsonFile);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return pdmFile;
    }

}
