package eventichs.api.eventichs_api.Controleurs

import eventichs.api.eventichs_api.Exceptions.PasConnectéException
import eventichs.api.eventichs_api.Modèle.Catégorie
import eventichs.api.eventichs_api.Services.CatégorieOrganisationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.security.Principal

@RestController
@RequestMapping("\${api.base-path:}")
@Tag(
    name = "CatégorieOrganisation",
    description = "Points d'accès aux ressources liées aux catégories d'organisations existantes sur le service."
)
class CatégorieOrganisationControleur(val service: CatégorieOrganisationService) {

    @Operation(
        summary = "Obtenir une catégorie spécifique par son ID.",
        description = "Retourne la catégorie qui porte l'ID spécifié dans tous les catégories du service.",
        operationId = "obtenirCategorieParID",
        responses = [
            ApiResponse(responseCode = "201", description = "Catégorie trouvé"),
            ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas connecté"),
            ApiResponse(responseCode = "403", description = "L'utilisateur n'as pas le droit de consulter cette catégorie"),
            ApiResponse(responseCode = "404", description = "La catégorie n'existe pas dans le service")
        ]
    )
    @GetMapping("/organisations/categories")
    fun obtenirCategories(principal: Principal?): List<Catégorie>{
        if (principal == null) {
            throw PasConnectéException("L'utilisateur n'est pas connecté.")
        }
        return service.chercherTous(principal.name)
    }

    @Operation(
        summary = "Obtenir une catégorie spécifique par son ID.",
        description = "Retourne la catégorie qui porte l'ID spécifié dans tous les catégories du service.",
        operationId = "obtenirCategorieParID",
        responses = [
            ApiResponse(responseCode = "201", description = "Catégorie trouvé"),
            ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas connecté"),
            ApiResponse(responseCode = "403", description = "L'utilisateur n'as pas le droit de consulter cette catégorie"),
            ApiResponse(responseCode = "404", description = "La catégorie n'existe pas dans le service")
        ]
    )
    @GetMapping("/organisations/categories/{id}")
    fun obtenirCategorieParID(@PathVariable id: Int, principal: Principal?): Catégorie?{
        if (principal == null) {
            throw PasConnectéException("L'utilisateur n'est pas connecté.")
        }
        return service.chercherParID(id, principal.name)
    }

    @Operation(
        summary = "Modifier une catégorie existante.",
        description = "Modifie et retourne la catégorie spécifiée dans la base de données.",
        operationId = "modifierCategorie",
        responses = [
            ApiResponse(responseCode = "200", description = "La catégorie a été modifié"),
            ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas connecté"),
            ApiResponse(responseCode = "403", description = "L'utilisateur n'as pas le droit de consulter cette catégorie"),
            ApiResponse(responseCode = "404", description = "La catégorie n'existe pas.")
        ]
    )
    @PutMapping("/organisations/categories/{id}")
    fun modifierCategorie(@PathVariable id: Int, @RequestBody catégorie: Catégorie, principal: Principal?): ResponseEntity<Catégorie> {
        if (principal == null) {
            throw PasConnectéException("L'utilisateur n'est pas connecté.")
        }
        val catégorieModifiée : Catégorie? = service.modifierCatégorie(catégorie, principal)
        val uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/categories/{id}")
            .buildAndExpand(catégorie.id)
            .toUri()
        return ResponseEntity.created(uri).body(catégorieModifiée)
    }

    @Operation(
        summary = "Ajouter une nouvelle catégorie.",
        description = "Créer et ajouter une nouvelle catégorie dans la base de données.",
        operationId = "ajouterCategorie",
        responses = [
            ApiResponse(responseCode = "200", description = "La catégorie a été ajouté"),
            ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas connecté"),
            ApiResponse(responseCode = "403", description = "L'utilisateur n'as pas le droit de consulter cette catégorie"),
            ApiResponse(responseCode = "404", description = "La catégorie n'existe pas")
        ]
    )
    @PostMapping("/organisations/categories")
    fun ajouterCategorie(@RequestBody catégorie: Catégorie, principal: Principal?): ResponseEntity<Catégorie> {
        if (principal == null) {
            throw PasConnectéException("L'utilisateur n'est pas connecté.")
        }
        val nouvelleCatégorie: Catégorie? = service.ajouterCatégorie(catégorie, principal)
        val uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/categories/{id}")
            .buildAndExpand(catégorie.id)
            .toUri()
        return ResponseEntity.created(uri).body(nouvelleCatégorie)
    }

    @Operation(
        summary = "Supprimer une catégorie spécifique par son ID.",
        description = "Supprime et retourne la catégorie qui porte l'ID spécifié dans tous les catégories du service.",
        operationId = "effacerCategorie",
        responses = [
            ApiResponse(responseCode = "200", description = "La catégorie a été supprimé"),
            ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas connecté"),
            ApiResponse(responseCode = "403", description = "L'utilisateur n'as pas le droit de consulter cette catégorie"),
            ApiResponse(responseCode = "404", description = "La catégorie n'existe pas")
        ]
    )
    @DeleteMapping("/organisations/categories/{id}")
    fun effacerCategorie(@PathVariable id: Int, principal: Principal?): Catégorie? {
        if (principal == null) {
            throw PasConnectéException("L'utilisateur n'est pas connecté.")
        }
        return service.supprimerParID(id, principal.name)
    }
}