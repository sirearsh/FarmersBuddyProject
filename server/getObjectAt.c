void printJSON(int);
int main(int argc, char**args) {
    int id=0;
    while (*args[1]!='\0') {
        id=id*10+*(args[1]++)-'0';
    }
    printJSON(id);
    return 0;
}