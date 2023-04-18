//
// Created by Daniel Silva on 27/11/2022.
//

#ifndef STRINGS_NEWSTRINGS_H
#define STRINGS_NEWSTRINGS_H

char n_esimo( char *String, int Posicao );
char * strduplica(char *String);
int mystrlen(const char *String);
char * mystrcpy(char *Destino, char *Fonte);
char * mystrcat(char *Destino, const char *Fonte);
int mystrcountc(const char *String, char Car);
char * mystrrev(char *String);
char * mystrpack(char *String);
char * mystrpack_while(char *String);
char * inimai(char *String);
int myatoi(char *String);
int strcount(char *String);
char * strsubst(char *String, char oldCar, char newCar);

#endif //STRINGS_NEWSTRINGS_H
