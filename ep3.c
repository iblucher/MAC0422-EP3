/*******************************************************************************
 *  EP3 de MAC0422 (Sistemas Operacionais)
 *
 *  Isabela Blucher - 9298170
 *  Andre Victor dos Santos Nakazawa - 9298336
 * 
 *  SIMULADOR DE GERÊNCIA DE MEMÓRIA
 *
*******************************************************************************/

#include <stdio.h> 
#include <stdlib.h>
#include <readline/readline.h>
#include <readline/history.h>

#define MAX_COMMAND_LINE 2048

static char *line = (char *) NULL;
static char *args[MAX_COMMAND_LINE];

/* Função reutilizada e adaptada do shell do EP1 
 * Devolve 0 se deu erro ou não precisa fazer nada (linha em branco),
 * 1 caso deu certo */
int type_prompt (void) {
    char buffer[256], prompt[512];

    printf ("(ep3): ");

    /* If the buffer has already been allocated,
       return the memory to the free pool. (CODIGO COPIADO DO
       GNU READLINE) */
    if (line)
        free (line), line = (char *) NULL;

    /* Get a line from the user. [[[O outro jeito de fazer o prompt dava
       erro, eu conseguia apagar o resultado do cwd com backspace ?!]]] */
    line = readline (prompt);

    if (!(line && *line))
        return 0;

    /* If the line has any text in it,
       save it on the history. */
    if (line && *line)
        add_history (line);

    return 1;
}

/* Tokeniza o entrada do usuario armazenada em line e separa os
argumentos a serem executados em args. */
void read_command () {
    int argc = 0;
    char *token;

    token = line - 1;
    while ((token = strchr (token + 1, ' ')) != NULL)
        argc++;

    argc = 0, token = strtok (line, " ");
    while (token != NULL) {
        args[argc++] = token;
        token = strtok (NULL, " ");
    }
    args[argc] = NULL;
}

void carrega (char *nome) {
	FILE *arquivo;
	arquivo = fopen(nome, "r");
	/* código */
	fclose (arquivo);
}


int main (void) {
	int espaco = 0, substitui = 0;
    char *command = NULL;

    while (1) {
        if (!type_prompt ())
            continue;

        read_command ();
        command = *args;

        if (strcmp (command, "carrega") == 0)
            carrega (*(args+1));
        else if (strcmp (command, "espaco") == 0)
            espaco = *(args+1);
        else if (strcmp (command, "substitui") == 0)
            substitui = *(args+1);
        else if (strcmp (command, "executa") == 0)
            executa();
        else if (strcmp (command, "sai") == 0)
            break;
    }

    return EXIT_SUCCESS;
}