/*
 * COPYRIGHT. HSBC HOLDINGS PLC 2017. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of HSBC Holdings plc.
 */
package com.angularAppDemoRepo.pojo;

import java.util.List;

/**
 * <p>
 * <b> TODO : Insert description of the class's responsibility/role. </b>
 * </p>
 */
public class DynamicViewPojo {
    private String groupType;
    private String groupName;
    private String activeDirectoryName;
    private String groupDescription;
    private List<AccessDataPojo> accessData;

    /**
     * @return the groupType
     */
    public String getGroupType() {
        return this.groupType;
    }

    /**
     * @param groupType
     *            the groupType to set
     */
    public void setGroupType(final String groupType) {
        this.groupType = groupType;
    }

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return this.groupName;
    }

    /**
     * @param groupName
     *            the groupName to set
     */
    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return the activeDirectoryName
     */
    public String getActiveDirectoryName() {
        return this.activeDirectoryName;
    }

    /**
     * @param activeDirectoryName
     *            the activeDirectoryName to set
     */
    public void setActiveDirectoryName(final String activeDirectoryName) {
        this.activeDirectoryName = activeDirectoryName;
    }

    /**
     * @return the groupDescription
     */
    public String getGroupDescription() {
        return this.groupDescription;
    }

    /**
     * @param groupDescription
     *            the groupDescription to set
     */
    public void setGroupDescription(final String groupDescription) {
        this.groupDescription = groupDescription;
    }

    /**
     * @return the accessData
     */
    public List<AccessDataPojo> getAccessData() {
        return this.accessData;
    }

    /**
     * @param accessData
     *            the accessData to set
     */
    public void setAccessData(final List<AccessDataPojo> accessData) {
        this.accessData = accessData;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "[groupType=" + this.groupType + ", groupName=" + this.groupName + ", activeDirectoryName="
            + this.activeDirectoryName + ", groupDescription=" + this.groupDescription + ", accessData=" + this.accessData + "]";
    }


}
