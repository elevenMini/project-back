package com.eleven.miniproject.user.entity;

import jakarta.validation.GroupSequence;

@GroupSequence({ValidationGroups.NotBlankGroup.class, ValidationGroups.PatternCheckGroup.class})
public interface ValidationSequence {
}
