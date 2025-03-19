package com.makers.princemaker.dto

import com.makers.princemaker.controller.CreatePrince
import com.makers.princemaker.type.PrinceLevel
import com.makers.princemaker.type.SkillType


// 컴파일 오류를 잡기위해 리턴 타입 선언
fun dummyCreatePrinceRequest():CreatePrince.Request =
    CreatePrince.Request(
        princeLevel = PrinceLevel.JUNIOR_PRINCE,
        skillType = SkillType.WARRIOR,
        experienceYears = 10,
        princeId = "princeId",
        name = "name",
        age = 35

    )