package com.xiben.pm.md.bean;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.xiben.pm.md.pojo.MdEmployeeEntity;

public class MdEmployeeEx extends MdEmployeeEntity  {

	 private Integer role;

		public Integer getRole() {
			return role;
		}

		public void setRole(Integer role) {
			this.role = role;
		}
		
		@Override
		public String toString() {
			 return ReflectionToStringBuilder.toString(this); 
		}
}
