package com.eatsmap.module.notice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Notice findNoticeByNoticeCode(String code);
}
