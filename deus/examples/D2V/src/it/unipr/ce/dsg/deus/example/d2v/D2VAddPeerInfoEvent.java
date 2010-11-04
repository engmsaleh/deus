package it.unipr.ce.dsg.deus.example.d2v;

import java.util.Properties;

import it.unipr.ce.dsg.deus.core.InvalidParamsException;
import it.unipr.ce.dsg.deus.core.NodeEvent;
import it.unipr.ce.dsg.deus.core.RunException;
import it.unipr.ce.dsg.deus.core.Process;
import it.unipr.ce.dsg.deus.example.d2v.peer.D2VPeerDescriptor;


public class D2VAddPeerInfoEvent extends NodeEvent {

	private D2VPeerDescriptor peerInfo = null;
	
	public D2VAddPeerInfoEvent(String id, Properties params, Process parentProcess) 
	throws InvalidParamsException {
		super(id,params,parentProcess);
	}

	
	public void run() throws RunException {

		D2VPeer currPeer = (D2VPeer) getAssociatedNode();
	
		//System.out.println("VT:"+triggeringTime+"Key:"+currPeer.getKey()+" ADD PEER INFO EVENT FOR Key: " + peerInfo.getKey());
		
		//currPeer.setSentMessages(currPeer.getSentMessages() + 1);
		currPeer.incrementSentMessages();
		double kbValue = 0.0;
		String messageString = "ADD_INFO";
		kbValue = ((double)messageString.getBytes().length) / 1000.0;
		currPeer.addSentKbAmountForDGT(kbValue + D2VPeerDescriptor.getStructureKbLenght());
		currPeer.insertPeer("D2VAddPeerInfoEvent",peerInfo,triggeringTime);
	}
	
	public Object clone() {
		D2VAddPeerInfoEvent clone = (D2VAddPeerInfoEvent) super.clone();
		clone.peerInfo = null;
		return clone;
	}


	public D2VPeerDescriptor getPeerInfo() {
		return peerInfo;
	}


	public void setPeerInfo(D2VPeerDescriptor peerInfo) {
		this.peerInfo = peerInfo;
	}
}