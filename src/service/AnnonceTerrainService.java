/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.AnnonceTerrain;
import bean.PhotoTerrain;
import bean.Quartier;
import bean.TypeTerrain;
import java.util.List;

/**
 *
 * @author Jawadoo
 */
public class AnnonceTerrainService  extends AbstractFacade<AnnonceTerrain>{
    
    public AnnonceTerrainService() {
        super(AnnonceTerrain.class);
    }
    PhotoTerrainService photoTerrainService = new PhotoTerrainService();
    
//    private void intDB(){
//        for (int i = 0; i < 10; i++) {
//            creeAnnonce("EE"+i, 2*i);
//        }
//        
//    }
    
     public AnnonceTerrain creeAnnonce(String refAnnonce, double surface,double prix,String description, Quartier quartier ,TypeTerrain typeTerrain ) {
        AnnonceTerrain at = new AnnonceTerrain();
        at.setRefAnnonce(refAnnonce);
        at.setSurface(surface);
        at.setDescription(description);
        at.setPrix(prix);
        at.setQuartier(quartier);
        at.setTypeTerrain(typeTerrain);
        at.setCloture(true);
        create(at);
        return at;
    }
   public int clotureAnnonce(AnnonceTerrain a) {
        if (a.isCloture() == false) {
            return -1;
           } 
        else {
            a.setCloture(false);
            edit(a);
            return 1;
        }
    }
    
     public void saveAnnonce(AnnonceTerrain annonceTerrain, List<PhotoTerrain> PhotoTerrains){ // crée une Annonce et ces photos
         create(annonceTerrain);
         for (PhotoTerrain photoTerrain : PhotoTerrains)        
 {
             photoTerrain.setAnnonceTerrain(annonceTerrain);
             create(annonceTerrain);
             photoTerrainService.create(photoTerrain);
         }
     }
     
      public List<AnnonceTerrain> searchByCreteria(String refAnnonce, Double surface, Quartier quartier ,TypeTerrain typeTerrain){
        String query =constructQuery(refAnnonce, surface, quartier, typeTerrain);
        return getEntityManager().createQuery(query).getResultList();
    }
      
    private String constructQuery(String refAnnonce, Double surface,  Quartier quartier ,TypeTerrain typeTerrain){
        String query = "SELECT a FROM AnnonceTerrain a WHERE 1=1";
        if(refAnnonce != null && !refAnnonce.equals("")){
            query += " AND a.refAnnonce = '"+refAnnonce+"'";
            return query ;
        }
        if(surface!= null&& !surface.toString().equals("")){
            query += " AND a.surface >= '"+surface+"'";
        }
        
        if(quartier!= null && !quartier.toString().equals("")){
            query += " AND a.quartier <= '"+quartier+"'";
        }
        if(typeTerrain!= null && !typeTerrain.toString().equals("")){
            query += " AND a.typeTerrain <= '"+typeTerrain+"'";
        }
        return query;
        
        
    }
    
    public void deleteByIdAnnonce(String refAnnonce){       //suprimer l'annonce et ces photos
         photoTerrainService.deleteByRefAnnonce(refAnnonce);
         remove (new AnnonceTerrain( refAnnonce ));
     }
      public List<AnnonceTerrain> findByIdQuartier(long idQuartier) { // trouver les annonce associe a un Quartier 
        String query = "SELECT a FROM AnnonceTerrain a where a.quartier.idQuartier='" +idQuartier + "' ";
        return getEntityManager().createQuery(query).getResultList();
    } 
}