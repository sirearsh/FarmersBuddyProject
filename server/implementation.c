#include <stdio.h>
#include <string.h>

const char *dataFile = "userBase.bin";

typedef struct User {
    char userName[50];
    char username[50];
    char password[50];
    char cityName[50];
    int landArea;
} User;

void AppendObject(User *obj) {
    FILE *file = fopen(dataFile, "a");
    fwrite(obj, sizeof(User), 1, file);
    fclose(file);
}

int getFileSize() {
    FILE *file = fopen(dataFile, "r");
    fseek(file, 0, SEEK_END);
    int res = (long long)ftell(file) / sizeof(User);
    fseek(file, 0, SEEK_SET);
    return res;
}

void getObjectAt(int idx, User *obj) {
    FILE *file = fopen(dataFile, "r");
    fseek(file, idx * sizeof(User), SEEK_SET);
    fread(obj, sizeof(User), 1, file);
}

void addUser(char *userName, char *username, char *password, char *cityName, int landArea) {
    User obj;
    strcpy(obj.userName, userName);
    strcpy(obj.username, username);
    strcpy(obj.password, password);
    strcpy(obj.cityName, cityName);
    obj.landArea = landArea;
    AppendObject(&obj);
}

int findUser(char *username, char *password) {
    User obj;
    for (int i=0; i<getFileSize(); ++i) {
        getObjectAt(i, &obj);
        if (strcmp(obj.username, username) == 0) {
            if (strcmp(obj.password, password) == 0)
                return i;
            return -1;
        }
    }
    return -1;
}

void printJSON(int id) {
    User obj;
    getObjectAt(id, &obj);
    printf("{\n"
           "\t\"status\":true,\n"
           "\t\"userName\":\"%s\",\n"
           "\t\"cityName\":\"%s\",\n"
           "\t\"landArea\":%d\n"
           "}\n",
           obj.userName, obj.cityName, obj.landArea);
}