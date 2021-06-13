package com.simpson.domain.property;

import java.util.List;
import java.util.Optional;

public class Html {
    private List<ErrorFile> errorFiles;
    
    public List<ErrorFile> getErrorFiles() {
        return errorFiles;
    }
    
    public Optional<ErrorFile> getErrorFile(int errorCode) {
        String type = String.valueOf(errorCode);
        for (ErrorFile errorFile : errorFiles) {
            if (errorFile.getErrorType().compareTo(type) == 0)
                return Optional.of(errorFile);
        }
        return Optional.empty();
    }
}
