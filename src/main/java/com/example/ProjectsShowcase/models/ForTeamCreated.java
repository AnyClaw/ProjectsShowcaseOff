package com.example.ProjectsShowcase.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ForTeamCreated {

    private String name;
    private ArrayList<Long> teammatesId;

}
