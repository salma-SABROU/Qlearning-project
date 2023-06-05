# Le Q-learning
Le Q-learning est un algorithme d'apprentissage par renforcement utilisé pour résoudre des problèmes d'apprentissage basés sur la prise de décision séquentielle. Il est principalement utilisé dans des environnements où un agent prend des décisions pour maximiser une récompense cumulative. Dans un système multi-agents, chaque agent est un apprenant indépendant qui interagit avec l'environnement et prend des décisions en fonction de son état actuel.

## L'algorithme de Q-learning
Pour utiliser l'algorithme de Q-learning, vous pouvez suivre les étapes suivantes :

  1- Définissez l'environnement : Identifiez les agents impliqués et définissez les états possibles, les actions disponibles et les récompenses pour chaque agent. Assurez-vous que l'environnement est approprié pour l'apprentissage par renforcement.

  2- Initialisez les valeurs Q : Créez une table (ou une fonction approximative) pour chaque agent pour stocker les valeurs Q. Les valeurs Q représentent la récompense attendue pour chaque paire état-action.

  3- Définissez les paramètres de l'algorithme : Déterminez les paramètres de l'algorithme de Q-learning tels que le taux d'apprentissage (learning rate), le facteur d'actualisation (discount factor) et l'exploration/exploitation (exploration/exploitation trade-off).

  4- Définissez la boucle d'apprentissage : Pour chaque épisode, répétez les étapes suivantes jusqu'à ce que l'arrêt soit atteint :
    
    a. Sélectionnez une action pour chaque agent en utilisant une politique basée sur les valeurs Q (par exemple, epsilon-greedy).
    
    b. Exécutez les actions sélectionnées dans l'environnement et observez les récompenses et les nouveaux états.
    
    c. Mettez à jour les valeurs Q pour chaque agent en utilisant la formule de mise à jour du Q-learning.
    
    d. Passez à l'état suivant et répétez les étapes précédentes jusqu'à la fin de l'épisode.

  5- Répétez l'apprentissage : Répétez la boucle d'apprentissage pour un certain nombre d'épisodes jusqu'à ce que les agents aient convergé vers une politique optimale.

## Implementation sequentiel (/src/sequential)
Cet implémente de l'algorithme Q-learning pour résoudre un problème de grille. Voici une explication étape par étape de ce que fait le code :

  1- Définition des paramètres : ALPHA, GAMMA, MAX_EPOCH, GRID_SIZE et ACTION_SIZE sont les paramètres définis pour l'algorithme Q-learning.

  2- Initialisation de la grille : La grille est une représentation du problème sous forme de matrice, où chaque élément de la matrice représente une case dans la grille. Les valeurs positives indiquent des récompenses potentielles, tandis que les valeurs négatives indiquent des obstacles ou des zones non souhaitables.

  3- Méthode resetState() : Cette méthode réinitialise les coordonnées de l'agent à l'état initial de la grille.

  4- Méthode chooseAction(double eps) : Cette méthode est utilisée pour choisir une action à partir de l'état actuel de l'agent. Elle utilise une approche epsilon-greedy pour l'exploration et l'exploitation. Si un nombre aléatoire est inférieur à epsilon (eps), une action aléatoire est choisie pour l'exploration. Sinon, l'action avec la valeur Q maximale pour l'état actuel est choisie pour l'exploitation.

  5- Méthode executeAction(int act) : Cette méthode exécute l'action choisie par l'agent en mettant à jour ses coordonnées dans la grille. Elle gère également les limites de la grille pour éviter que l'agent ne sorte de la grille.

  6- Méthode finished() : Cette méthode vérifie si l'état actuel de l'agent est un état final, c'est-à-dire si la récompense correspondante dans la grille est positive.

  7- Méthode showResult() : Cette méthode affiche la table Q résultante après l'apprentissage Q-learning. Elle parcourt chaque ligne de la table Q et affiche les valeurs des actions pour chaque état.

  8- Méthode runQlearning() : Cette méthode exécute l'algorithme Q-learning. Elle réinitialise l'état de l'agent, puis effectue des itérations jusqu'à ce que l'état final soit atteint. À chaque itération, elle choisit une action, exécute l'action, met à jour la table Q et continue jusqu'à ce que l'état final soit atteint. Elle appelle ensuite la méthode showResult() pour afficher les résultats de l'apprentissage.

Ce code peut être utilisé pour résoudre des problèmes de grille spécifiques en utilisant l'algorithme Q-learning. Vous pouvez personnaliser les valeurs de la grille, les récompenses et les obstacles pour adapter le problème à votre cas d'utilisation.
![image](https://github.com/salma-SABROU/Qlearning-project/assets/129564311/2584ec85-6444-4204-9d54-3d23e0241ce5)


## Implementation multi agents (/src/sma)
Lorsque vous l'appliquez à un système multi-agents, chaque agent doit apprendre indépendamment les valeurs Q en interagissant avec l'environnement. Cela peut entraîner des problèmes de convergence ou de coordination entre les agents. Des techniques telles que le Q-learning coopératif, le Q-learning centré sur l'opposant ou d'autres approches multi-agents peuvent être utilisées pour améliorer la performance globale du système et la coordination entre les agents.

Dans notre implemetation est existe un agent main qui charger de recuperer tous les Q table de chaque agents et donne comme resultats final les actions de l'agent qui a trouver le plus petit chemin
![image](https://github.com/salma-SABROU/Qlearning-project/assets/129564311/c5e3e3f3-04ac-457f-b46f-16850df5b8ea)
Parmi les 5 agents qui implemente l'algorithme de Q-learning dans les meme conditions , le main containere retourne le premeir agents qui a l'index 0
![image](https://github.com/salma-SABROU/Qlearning-project/assets/129564311/489e72cc-ddf7-46b5-b008-3d65a7e7b494)





