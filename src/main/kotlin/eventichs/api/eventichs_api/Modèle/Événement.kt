package eventichs.api.eventichs_api.Modèle;

data class Événement(val id: Int, val nom: String, val dateDebut: Date, val dateFin: Date, val type: String, val categorie_Id: String, val description: String, val photo: String, val organisation_Id: String)