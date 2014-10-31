package com.nationsky.adapter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class MapAdapter extends XmlAdapter<MapEntity[], Map> {

	@Override
	public MapEntity[] marshal(Map map) throws Exception {
		// TODO Auto-generated method stub
		MapEntity[] list = new MapEntity[map.size()];
		Set keyset = map.keySet();
		int index = 0;
		for (Iterator keyItor = keyset.iterator(); keyItor.hasNext();) {
			MapEntity item = new MapEntity();
			item.setKey(keyItor.next());
			item.setValue(map.get(item.getKey()));
			list[index++] = item;
		}
		return list;
	}

	@Override
	public Map unmarshal(MapEntity[] list) throws Exception {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		for (int i = 0; i < list.length; i++) {
			MapEntity item = list[i];
			map.put(item.getKey(), item.getValue());
		}
		return map;
	}

}
