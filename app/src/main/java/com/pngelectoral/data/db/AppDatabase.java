package com.pngelectoral.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.pngelectoral.data.dao.ClanListDao;
import com.pngelectoral.data.dao.DistrictListDao;
import com.pngelectoral.data.dao.DpoListDao;
import com.pngelectoral.data.dao.GeoClanListDao;
import com.pngelectoral.data.dao.GeoListDao;
import com.pngelectoral.data.dao.LLGListDao;
import com.pngelectoral.data.dao.LocationListDao;
import com.pngelectoral.data.dao.OccupationListDao;
import com.pngelectoral.data.dao.ProvinceListDao;
import com.pngelectoral.data.dao.RegionListDao;
import com.pngelectoral.data.dao.TransferInVotersListDao;
import com.pngelectoral.data.dao.VotersListDao;
import com.pngelectoral.data.dao.WardListDao;
import com.pngelectoral.ui.model.ClanListData;
import com.pngelectoral.ui.model.DistrictList;
import com.pngelectoral.ui.model.DpoList;
import com.pngelectoral.ui.model.LLGList;
import com.pngelectoral.ui.model.LocationList;
import com.pngelectoral.ui.model.MKAClanListData;
import com.pngelectoral.ui.model.MKAGeoListData;
import com.pngelectoral.ui.model.MKAGeoListModel;
import com.pngelectoral.ui.model.OccupationList;
import com.pngelectoral.ui.model.ProvinceList;
import com.pngelectoral.ui.model.RegionList;
import com.pngelectoral.ui.model.VotersList;
import com.pngelectoral.ui.model.WardList;

@Database(entities = {VotersList.class, RegionList.class, ProvinceList.class
        , DistrictList.class, LLGList.class
        , WardList.class, LocationList.class
        , OccupationList.class, DpoList.class, MKAGeoListData.class, MKAClanListData.class, ClanListData.class }, version = 4)

public abstract class AppDatabase extends RoomDatabase {

    public abstract VotersListDao votersListDao();

    public abstract RegionListDao regionListDao();

    public abstract ProvinceListDao provinceListDao();

    public abstract DistrictListDao districtListDao();

    public abstract LLGListDao llgListDao();

    public abstract WardListDao wardListDao();

    public abstract LocationListDao locationListDao();

    public abstract OccupationListDao occupationListDao();

    public abstract TransferInVotersListDao transferInVotersListDao();

    public abstract DpoListDao dpoListDao();

    public abstract GeoListDao geoListDao();

    public abstract ClanListDao clanListDao();

    public abstract GeoClanListDao geoClanListDao();

}
