package sio.tp3;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.util.Callback;
import sio.tp3.Model.Tache;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class TP3Controller implements Initializable {
    private HashMap<String, HashMap<String, ArrayList<Tache>>> mesTaches;
    TreeItem racine;
    @FXML
    private ListView lstThemes;
    @FXML
    private ListView lstProjets;
    @FXML
    private TreeView tvTaches;
    @FXML
    private ComboBox cboDeveloppeurs;
    @FXML
    private Button cmdValider;
    @FXML
    private TextField txtNomTache;
    TreeItem noeudTheme;
    TreeItem noeudProjets;
    TreeItem noeudTaches;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mesTaches = new HashMap<>();
        racine = new TreeItem("Mes tâches");
        racine.setExpanded(true);
        tvTaches.setRoot(racine);

        cboDeveloppeurs.getItems().addAll("Enzo","Noa","Lilou","Milo");
        cboDeveloppeurs.getSelectionModel().selectFirst();

        lstThemes.getItems().addAll("Mobile","Web","Réseau");

        for(int i = 1 ; i<=10 ; i++)
        {
            lstProjets.getItems().add("Projet n°" + i);
        }
    }

    @FXML
    public void cmdValiderClicked(Event event)
    {
        if (txtNomTache.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Saisir une tâche");
            alert.showAndWait();
        } else if (lstProjets.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Saisir un projet");
            alert.showAndWait();
        }else if (lstThemes.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Saisir un thème");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Vous avez ajouté une têche");
            alert.setHeaderText("Le nom de la tâche est :" + txtNomTache.getText() + " et est affilié à :" + cboDeveloppeurs.getSelectionModel().getSelectedItem());

            alert.showAndWait();

            String nomTache = txtNomTache.getText();
            String nomDeveloppeur = cboDeveloppeurs.getSelectionModel().getSelectedItem().toString();
            String nomTheme = lstThemes.getSelectionModel().getSelectedItem().toString();
            String nomProjets = lstProjets.getSelectionModel().getSelectedItem().toString();
            Boolean estTerminée = false;

            Tache tacheInfo = new Tache(nomTache, nomDeveloppeur, estTerminée);



            if (!(mesTaches.containsKey(nomTheme))) {
                mesTaches.put(nomTheme, new HashMap<>());
                mesTaches.get(nomTheme).put(nomProjets, new ArrayList<>());

            }
            mesTaches.get(nomTheme).get(nomProjets).add(tacheInfo);

            int compteurTache = 1;





            noeudTheme = new TreeItem<>(nomTheme);
            HashMap<String, ArrayList<Tache>> mesProjets = mesTaches.get(nomTheme);



            for (String lesProjets : mesProjets.keySet()) {
                noeudProjets = new TreeItem<>(lesProjets);
                ArrayList<Tache> maTache = mesProjets.get(nomProjets);


                for (Tache tacheEnCours : maTache) {


                        noeudTaches = new TreeItem<>(cboDeveloppeurs.getSelectionModel().getSelectedItem() + " : " + nomTache + " : " + tacheEnCours.isEstTerminee());

                        noeudProjets.getChildren().add(noeudTaches);
                        if (mesTaches.containsKey(nomProjets) && mesTaches.containsKey(nomTheme) )
                        {
                            ajoutTacheDansTreeView(nomTheme,nomProjets);
                        }
                            compteurTache++;
                }

                noeudTheme.getChildren().add(noeudProjets);

            }

            racine.getChildren().add(noeudTheme);




        }

    }
    public void ajoutTacheDansTreeView(String theme,String projet)
    {
        TreeItem noeudThemeExistant = null;
        TreeItem noeudProjetExistant = null;

        ObservableList<TreeItem> ajoutTaches = racine.getChildren();

        for (TreeItem itemTheme : ajoutTaches)
        {
            if(itemTheme.getValue().equals(theme)){
                    noeudThemeExistant = itemTheme;

            }
        }

        if (noeudThemeExistant == null)
        {
            noeudThemeExistant = new TreeItem<>(theme);
            ajoutTaches.add(noeudThemeExistant);
        }
        for (TreeItem itemProjet : ajoutTaches)
        {
            if(itemProjet.getValue().equals(projet)){
                noeudProjetExistant = itemProjet;

            }
        }

        if (noeudProjetExistant == null)
        {
            noeudProjetExistant = new TreeItem<>(projet);
            ajoutTaches.add(noeudProjetExistant);
        }



    }

    @FXML
    public void tvTachesClicked(Event event)
    {

    }
}