package eventichs.api.eventichs_api.Controleurs
import eventichs.api.eventichs_api.Exceptions.PasConnectéException
import eventichs.api.eventichs_api.Modèle.Événement
import eventichs.api.eventichs_api.Services.EvenementService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RequestMapping("\${api.base-path:}")
@RestController
@Tag(
    name = "Événement",
    description = "Points d'accès aux ressources liées aux événements éxistants sur le service."
)
class EvenementControleur(val service : EvenementService) {
    @Operation(
        summary = "Obtenir la liste des évenements.",
        description = "Retourne la liste de tous les évènements dans le service.",
        operationId = "obtenirEvenements"
    )
    @GetMapping("/evenements")
    fun obtenirEvenements(principal: Principal?) : List<Événement>{
        if (principal == null) {
            throw PasConnectéException("L'utilisateur n'est pas connecté.")
        }
        return service.chercherTous()
    }
    @Operation(
        summary = "Obtenir un évenement spécifique par son ID.",
        description = "Retourne l'évènement qui porte l'ID spécifié dans tous les évènements du service",
        operationId = "obtenirEvenementParID"
    )
    @GetMapping("/evenements/{id}")
    fun obtenirEvenementParId(@PathVariable id: Int) = service.chercherParID(id)
    @Operation(
        summary = "Supprimer un évenement spécifique par son ID.",
        description = "Supprime et retourne l'évènement qui porte l'ID spécifié dans tous les évènements du service",
        operationId = "supprimerEvenement"
    )
    @GetMapping("/organisations/{nom}/evenements")
    fun obtenirEvenementParOrganisation(@PathVariable nom: String) = service.chercherParOrganisation(nom)
    @Operation(
            summary = "Supprimer un évenement spécifique par son ID.",
            description = "Supprime et retourne l'évènement qui porte l'ID spécifié dans tous les évènements du service",
            operationId = "supprimerEvenement"
    )
    @DeleteMapping("/evenements/{id}")
    fun supprimerEvenement(@PathVariable id: Int) = service.supprimerParID(id)
    @Operation(
        summary = "Modifier un évènement existant.",
        description = "Modifie et retourne l'évènement spécifié dans la base de données",
        operationId = "modifierEvenement"
    )
    @PutMapping("/evenements/{id}")
    fun modifierEvenement(@PathVariable id: Int, @RequestBody evenement : Événement) = service.modifierEvenement(id,evenement)
    @Operation(
        summary = "Ajouter un nouvel évènement.",
        description = "Créée et ajoute un nouvel évènement dans la base de données",
        operationId = "ajouterEvenement"
    )
    @PostMapping("/evenements")
    fun ajouterEvenement(@RequestBody evenement : Événement) = service.ajouterEvenement(evenement)
    @Operation(
        summary = "Obtenir une liste d'évenements selon leur type.",
        description = "Retourne une liste d'évènements selon le type fournis.",
        operationId = "obtenirEvenementParType"
    )
    @GetMapping("/evenements/public")
    fun obtenirEvenementPublic() = service.chercherEvenementPublic()
}