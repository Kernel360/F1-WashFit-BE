package com.kernel360.bbs.service;

import com.kernel360.bbs.code.BBSErrorCode;
import com.kernel360.bbs.dto.BBSDto;
import com.kernel360.bbs.dto.BBSListDto;
import com.kernel360.bbs.entity.BBS;
import com.kernel360.bbs.repository.BBSRepository;
import com.kernel360.exception.BusinessException;
import com.kernel360.file.entity.File;
import com.kernel360.file.entity.FileReferType;
import com.kernel360.file.repository.FileRepository;
import com.kernel360.member.code.MemberErrorCode;
import com.kernel360.member.dto.MemberDto;
import com.kernel360.member.service.MemberService;
import com.kernel360.utils.file.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BBSService {
    private final FileUtils fileUtils;
    private final BBSRepository bbsRepository;
    private final MemberService memberService;
    private final FileRepository fileRepository;

    @Value("${aws.s3.bucket.url}")
    private String bucketUrl;
    private static final String REFERENCE_TYPE = "BBS";

    public Page<BBSListDto> getBBSWithConditionByPage(String type, String keyword, Pageable pageable) {

        return bbsRepository.getBBSWithConditionByPage(type, keyword, pageable);
    }

    public Slice<BBSListDto> getBBSWithConditionBySlice(String type, String keyword, Pageable pageable) {

        return bbsRepository.getBBSWithConditionBySlice(type, keyword, pageable);
    }

    public BBSDto getBBSView(Long bbsNo) {
        BBS entity = Optional.of(bbsRepository.findOneByBbsNo(bbsNo))
                             .orElseThrow(() -> new BusinessException(BBSErrorCode.FAILED_GET_BBS_VIEW));

        BBSDto result = BBSDto.from(entity, fileRepository.findByReferenceTypeAndReferenceNo(REFERENCE_TYPE, entity.getBbsNo()));

        return result;
    }

    public Page<BBSDto> getBBSReply(Long upperNo, Pageable pageable) {

        return bbsRepository.findAllByUpperNo(upperNo, pageable).map(entity -> BBSDto.from(entity, fileRepository.findByReferenceTypeAndReferenceNo(REFERENCE_TYPE, entity.getBbsNo())));
    }

    @Transactional
    public void saveBBS(BBSDto bbsDto, String id, List<MultipartFile> files) {

        MemberDto memberDto = Optional.of(memberService.findByMemberId(id))
                                      .orElseThrow(() -> new BusinessException(MemberErrorCode.FAILED_FIND_MEMBER_INFO));

        BBS bbs = bbsRepository.save(BBS.save(bbsDto.bbsNo(), bbsDto.upperNo(), bbsDto.type(), bbsDto.title(), bbsDto.contents(), true, 0L, memberDto.toEntity()));

        if(Objects.nonNull(files)){
            uploadFiles(files, bbs.getBbsNo());
        }
    }

    @Transactional
    public void deleteBBS(Long bbsNo) {

        bbsRepository.deleteByBbsNo(bbsNo);
    }

    private void uploadFiles(List<MultipartFile> files, Long bbsNo) {
        files.stream().forEach(file -> {
            String path = String.join("/", FileReferType.BBS.getDomain(), bbsNo.toString());
            String fileKey = fileUtils.upload(path, file);
            String fileUrl = String.join("/", bucketUrl, fileKey);

            File fileInfo = fileRepository.save(File.of(null, file.getOriginalFilename(), fileKey, fileUrl, FileReferType.BBS.getCode(), bbsNo));
            log.info("게시판 파일 등록 -> file_no {}", fileInfo.getFileNo());
        });
    }
}
