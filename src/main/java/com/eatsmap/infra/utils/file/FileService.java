package com.eatsmap.infra.utils.file;

import com.eatsmap.module.review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {

    private final FileRepository fileRepository;

    @Transactional
    public List<Fileinfo> createReviewFiles(List<MultipartFile> photos) {
        List<Fileinfo> reviewFiles = new ArrayList<>();
        FileUtil fileUtil = new FileUtil();
        for (MultipartFile photo : photos) {
            if (!photo.isEmpty()) {
                Fileinfo fileInfo = fileUtil.fileUpload(photo);
                fileRepository.save(fileInfo);
                reviewFiles.add(fileInfo);
            }
        }
        return reviewFiles;
    }

}
