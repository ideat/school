package mindware.com.utilities;

import com.google.gson.Gson;
import mindware.com.model.Parents;

import java.util.ArrayList;
import java.util.List;

public class UtilJson {
    public String listParentsToJsonFormat(List<Parents> lisParents){

        Gson gson = new Gson();
        return  gson.toJson(lisParents);

    }

    public List<Parents> deleteParentFromList(List<Parents> list, int id){
        List<Parents> parentsToRemove = new ArrayList<>();
        for(Parents parents: list){
            if(parents.getParentId().equals(id)){
                parentsToRemove.add(parents);
            }
        }
        list.removeAll(parentsToRemove);

        return list;
    }
}
