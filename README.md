API
----------

### User

#### Récupération d'un utilisateur :

    GET /user/<userId>
#### Renvoie un **utilisateur** :

> **id** : Identifiant de l'utilisateur
> **following** : Array d'utilisateurs

#### Créer un utilisateur :

    PUT /user/<userId>
Renvoie une **réponse**

> **code** : 0 si OK, 1 si KO
> **message** : description de l'erreur si code==1

#### Suivre un utilisateur

    PUT /user/<userId>/follow/<followId>

L'utilisateur *userId* suit l'utilisateur *followId*.  Renvoie une **réponse**.

#### Arrêter de suivre un utilisateur

    DELETE /user/<userId>/follow/<followId>

L'utilisateur *userId* ne suit l'utilisateur *followId*.  Renvoie une **réponse**.

#### Rechercher un utilisateur

    GET /user/<userId>/filter/<pattern>
    paramètre optionnel : filterFollowing (booléen)

La recherche est effectuée par l'utilisateur *userId*. Elle retourne une liste d'utilisateur dont l'**id** contient **pattern**.  La liste ne contient pas l'utilisateur **userId**.  Si **filterFollowing** est positionné, la liste ne contient pas les utilisateurs que **userId** suit déjà. 

### Piou

#### Récupérer la timeline
    GET /piou/<userId>

Retourne une liste de **piou** d'utilisateurs suivis par **userId**.  La liste contient également les **piou** de l'utilisateur **userId**.  Un **piou** est composé des éléments suivants :

> **userId** : id de l'utilisateur auteur du piou
> **message** : le message du piou
> **date** : date du piou (en ms depuis le 01/01/1970)

#### Envoyer un piou
    POST /piou/<userId>
    paramètre : un piou
    
Poste un piou au nom de **userId**.  Renvoie une **réponse**

### Follower

#### Récupérer la liste des followers

    GET /follower/<userId>

Retourne une liste d'**utilisateur** suivant **userId**.
