package Persistenz;

import VerarbeitungInterfaces.RaumfinderIF;

/**
 * Created by Alex on 07.08.2016.
 */

public interface RaumfinderFileAdapterIF {

    void save();
    RaumfinderIF load();
}