package com.eatsmap.module.group;

import com.eatsmap.module.group.dto.MemberGroupDTO;
import com.eatsmap.module.member.dto.MemberInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberGroupAdminService {

    private final MemberGroupRepository memberGroupRepository;

    public List<MemberGroupDTO> getAllMemberGroup(){
        // *** group 정보와 참가자 리스트 필요

        //  group + groupHistory (조인)
        //      i) 주인인 history 기준 조인
        //      ii) group 기준 조인


        return null;
    }
}
