package com.example.tennisres.oclasses;

public class RulesClass {

    private  int ruleId;
    private String rule;

    public RulesClass(int ruleId, String rule) {
        this.ruleId = ruleId;
        this.rule = rule;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
