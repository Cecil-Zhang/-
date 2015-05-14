package Control.userControl;

import java.util.ArrayList;
import vo.ApplicationVO;
import vo.InvitationVO;
import vo.toInvitationVO;

public interface MessageControlService {

	public void sendApplication(ApplicationVO appVo);
	public boolean sendInvitation(InvitationVO inVo);
	public void replyApplication(ApplicationVO appVo,boolean reply);
	public void replyInvitation(String fromId,String toId ,boolean reply);
	public void drawBackInvitat(String id);
	public ArrayList<ApplicationVO> getApplicationVO(String id);
	public ArrayList<toInvitationVO> getInvitationVO(String id);
}
