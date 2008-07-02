package it.unipr.ce.dsg.deus.impl.process;

import it.unipr.ce.dsg.deus.core.Engine;
import it.unipr.ce.dsg.deus.core.Event;
import it.unipr.ce.dsg.deus.core.InvalidParamsException;
import it.unipr.ce.dsg.deus.core.Node;
import it.unipr.ce.dsg.deus.core.Process;

import java.util.ArrayList;
import java.util.Properties;

public class TwoSpeedsPoissonProcess extends Process {
	private static final String FIRST_MEAN_ARRIVAL = "firstMeanArrival";
	private static final String SECOND_MEAN_ARRIVAL = "secondMeanArrival";
	private static final String VT_THRESHOLD = "vtThreshold";

	private float firstMeanArrival = 0;
	private float secondMeanArrival = 0;
	private float vtThreshold = 0;

	public TwoSpeedsPoissonProcess(String id, Properties params,
			ArrayList<Node> referencedNodes, ArrayList<Event> referencedEvents)
			throws InvalidParamsException {
		super(id, params, referencedNodes, referencedEvents);
		initialize();
	}

	public float getFirstMeanArrival() {
		return firstMeanArrival;
	}

	public float getSecondMeanArrival() {
		return secondMeanArrival;
	}
	
	public void initialize() throws InvalidParamsException {
		if (params.getProperty(FIRST_MEAN_ARRIVAL) == null)
			throw new InvalidParamsException(FIRST_MEAN_ARRIVAL
					+ " param is expected.");

		try {
			firstMeanArrival = Float.parseFloat(params.getProperty(FIRST_MEAN_ARRIVAL));
		} catch (NumberFormatException ex) {
			throw new InvalidParamsException(FIRST_MEAN_ARRIVAL
					+ " must be a valid float value.");
		}
		if (params.getProperty(SECOND_MEAN_ARRIVAL) == null)
			throw new InvalidParamsException(SECOND_MEAN_ARRIVAL
					+ " param is expected.");

		try {
			secondMeanArrival = Float.parseFloat(params.getProperty(SECOND_MEAN_ARRIVAL));
		} catch (NumberFormatException ex) {
			throw new InvalidParamsException(SECOND_MEAN_ARRIVAL
					+ " must be a valid float value.");
		}
		if (params.getProperty(VT_THRESHOLD) == null)
			throw new InvalidParamsException(VT_THRESHOLD
					+ " param is expected.");

		try {
			vtThreshold = Float.parseFloat(params.getProperty(VT_THRESHOLD));
		} catch (NumberFormatException ex) {
			throw new InvalidParamsException(VT_THRESHOLD
					+ " must be a valid float value.");
		}
	}

	public float getNextTriggeringTime(float virtualTime) {
		if (Engine.getDefault().getVirtualTime() < vtThreshold)
			return virtualTime + expRandom((float) 1 / firstMeanArrival);
		else
			return virtualTime + expRandom((float) 1 / secondMeanArrival);
	}
	
	// returns exponentially distributed random variable
	private float expRandom(float lambda) {
		float myRandom = (float) (-Math.log(Engine.getDefault().getSimulationRandom()
				.nextFloat()) / lambda);
		return myRandom;
	}
}