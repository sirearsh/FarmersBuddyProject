#include <stdio.h>

int findUser(char *, char *);
void printJSON(int);

int main (int argc, char **args) {
    char *username=args[1];
    char *passowrd=args[2];
    int id = findUser(username, passowrd);
    if (~id) {
        printJSON(id);
    }  else {
        printf("{\"status\":false}");
    } 
}