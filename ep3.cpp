/*******************************************************************************
 *  EP3 de MAC0422 (Sistemas Operacionais)
 *
 *  Isabela Blucher - 9298170
 *  Andre Victor dos Santos Nakazawa - 9298336
 * 
 *  SIMULADOR DE GERÊNCIA DE MEMÓRIA
 *
*******************************************************************************/

#include <cstdio> 
#include <cstdlib>
#include <sys/types.h>
#include <sys/stat.h>
#include <readline/readline.h>
#include <readline/history.h>
#include <vector>

#define MAX_COMMAND_LINE 2048

static char *line = (char *) NULL;
static char *args[MAX_COMMAND_LINE];
long tot, virt, s, p;
int espaco = 0, substitui = 0;

/* Função reutilizada e adaptada do shell do EP1 
 * Devolve 0 se deu erro ou não precisa fazer nada (linha em branco),
 * 1 caso deu certo */
int type_prompt (void) {
    /* If the buffer has already been allocated,
       return the memory to the free pool. (CODIGO COPIADO DO
       GNU READLINE) */
    if (line)
        free (line), line = (char *) NULL;

    /* Get a line from the user. [[[O outro jeito de fazer o prompt dava
       erro, eu conseguia apagar o resultado do cwd com backspace ?!]]] */
    line = readline ("(ep3): ");

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
	FILE *arquivo = NULL;
    arquivo = fopen (nome, "r");
    std::vector<process> plist;

    fscanf (arquivo, "%ld %ld %ld %ld", tot, virt, s, p);
    
    while () {
        
    }

	fclose (arquivo);
}

void executa (void) {

}

int main (void) {
    const char *command = NULL;

    while (1) {
        if (!type_prompt ())
            continue;

        read_command ();
        command = *args;

        if (command == NULL) command = "\0";

        if (strcmp (command, "carrega") == 0)
            carrega (*(args+1));
        else if (strcmp (command, "espaco") == 0)
            espaco = atoi (*(args+1));
        else if (strcmp (command, "substitui") == 0)
            substitui = atoi (*(args+1));
        else if (strcmp (command, "executa") == 0)
            executa();
        else if (strcmp (command, "sai") == 0)
            break;

    }

    return EXIT_SUCCESS;
}
