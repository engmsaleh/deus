package it.unipr.ce.dsg.deus.example.chordStreaming;

import it.unipr.ce.dsg.deus.core.InvalidParamsException;
import it.unipr.ce.dsg.deus.core.NodeEvent;
import it.unipr.ce.dsg.deus.core.Process;
import it.unipr.ce.dsg.deus.core.RunException;

import java.util.Properties;

/**
 * <p>
 * This Class is used to run the methods that publish all the peer's resources 
 * </p>
 * 
 * @author Matteo Agosti (matteo.agosti@unipr.it)
 * @author Marco Muro (marco.muro@studenti.unipr.it)
 *
 */
public class ChordPublishEvent extends NodeEvent{
	
	public ChordPublishEvent(String id, Properties params,
			Process parentProcess) throws InvalidParamsException {
		super(id, params, parentProcess);
	}

	@Override
	public void run() throws RunException {
		ChordPeer publishingNode = (ChordPeer) getAssociatedNode();
		publishingNode.publishResources();
	}

}
