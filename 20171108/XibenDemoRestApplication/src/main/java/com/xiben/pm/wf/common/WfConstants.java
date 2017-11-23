package com.xiben.pm.wf.common;

public class WfConstants {
	public static class WfInsGrade{
		//1=一般，2=中等，3=重要
		public static final int Normal = 1;
		public static final int Middle = 2;
		public static final int Important = 3;
	}
	
	public static class WfApproveType{
		//1=或审批，2=会签
		public static final int OR=1;
		public static final int AND = 2;
	}
	
	public static class WfType{
		//1=收文流程，2=发文流程，3=规章制度,99=其他流程
		public static final int ReceiveDoc = 1;
		public static final int SendDoc = 2;
		public static final int Rule=3;
		public static final int Other=4;
	}
	
	public static class WfNodeType{
		//1=发起节点，2=中间节点，3=最后节点
		public static final int StartNode=1;
		public static final int MiddleNode = 2;
		public static final int EndNode = 3;
	}
	
	public static class WfInsStatus{
		//1=未完成，2=已完成，3=已终止
		public static final int NoFinish =1;
		public static final int Finished = 2;
		public static final int Stoped = 3;
	}
	
	public static class WfInsNodeStatus{
		//1=未审批，2=审批中，3=已同意，4=已拒绝，99=删除
		public static final int NoApproved = 1;
		public static final int Approving = 2;
		public static final int Agree = 3;
		public static final int Rejected = 4;
		public static final int Deleted = 99;
	}
	
	public static class WfInsNodePartStatus{
		//1=未审批，2=审批中，3=已同意，4=已拒绝，99=已删除
		public static final int NoApproved = 1;
		public static final int Approving = 2;
		public static final int Agree = 3;
		public static final int Rejected = 4;
		public static final int Deleted = 99;		
	}
	
	public static class WfInsNodeUserStatus{
		//1=未审批，2=同意，3=拒绝，99=已删除
		public static final int NoApproved = 1;
		public static final int Agree = 2;
		public static final int Rejected = 3;
		public static final int Deleted = 99;		
	}
}
