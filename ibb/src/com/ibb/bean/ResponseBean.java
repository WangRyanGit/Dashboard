package com.ibb.bean;

import java.util.ArrayList;
import java.util.Map;

//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;

import com.google.protobuf.nano.MessageNano;

public class ResponseBean
{

	private int result;
    
    private byte[] data = new byte[0];
    private String stringData ="";
    private Map<String, String> mapData = null;
    public ArrayList<Exception> exceptions = new ArrayList<Exception>();
    
    public <T extends MessageNano> void setData(T msg)
    {
        this.data = MessageNano.toByteArray(msg);
    }
    
    public byte[] getData()
    {
        return this.data;
    }

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
    
	public ResponseBean(int result){
		this.result = result;
	}

	public String getStringData() {
		return stringData;
	}

	public void setStringData(String stringData) { this.stringData = stringData; }

	public Map<String, String> getMapData() {
		return mapData;
	}

	public void setMapData(Map<String, String> mapData) {
		this.mapData = mapData;
	}



}
