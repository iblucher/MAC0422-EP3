#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main () {
    int total = 64, virtual = 128, s = 4, p = 4;
    int i, j, linhas, n, pi, ti, t0, tf, b;

    printf("%d %d %d %d\n", total, virtual, s, p);
    srand((unsigned)time(NULL));

    linhas = 1+(rand()%100); // número de processos (entre 1 e 100)

    for (i = 0; i < linhas; i++) {
        t0 = 1 + (rand()%5); // tempo inicial (entre 1 e 5)
        tf = 1 + (rand()%10) + t0; // delta t + t0 (No máximo 10 seg de execução)
        b = 64 + (rand()%64); // total de memória usado (entre 64 e 128)
        printf("%d proc%d %d %d ", t0, i, tf, b);

        n = (tf-t0); // número de pags e instantes que ele as acessa (1 por segundo)

        for (j = 0; j < n; j++) {
            pi = 1 + (rand()%16); // o numero da pag que ele acessa (entre 1 e 16 = 64/4) 
            ti = j + t0;
            if (j == 0 || rand() > (0.3*RAND_MAX)) // Prob de 70% de nesse instante ele mexer em uma pagina
                printf("%d %d ", pi, ti); // Mexe em uma pelo menos.
        }
        printf("\n");
    }
    return 0;
}