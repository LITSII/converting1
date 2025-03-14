package com.makers.princemaker.entity;

import com.makers.princemaker.code.StatusCode
import com.makers.princemaker.code.StatusCode.HEALTHY
import com.makers.princemaker.type.PrinceLevel
import com.makers.princemaker.type.SkillType
import com.makers.princemaker.type.SkillType.WARRIOR
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.EnumType
import javax.persistence.Enumerated


// kotlin field test plugin 사용

fun dummyPrince(

    id: Long? = null,
    princeLevel: PrinceLevel=PrinceLevel.BABY_PRINCE,
    skillType: SkillType = WARRIOR,
    status: StatusCode = HEALTHY,
    experienceYears: Int = 23,
    princeId: String = "princeId",
    name: String = "name",
    age: Int = 28,
    createdAt: LocalDateTime? = LocalDateTime.now(),
    updatedAt: LocalDateTime? = LocalDateTime.now()

) = Prince(
    id = id,
    princeLevel = princeLevel,
    skillType = skillType,
    status = status,
    experienceYears = experienceYears,
    princeId = princeId,
    name = name,
    age = age,
    createdAt = createdAt,
    updatedAt = updatedAt

)