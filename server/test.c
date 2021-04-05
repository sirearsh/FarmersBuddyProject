int getFileSize();
void printJSON(int id);
int main() {
    for (int i=0; i<getFileSize(); ++i) {
        printJSON(i);
    }
}