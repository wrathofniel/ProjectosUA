/*
 * Created by Daniel Silva on 27/11/2022.
 * @TODO:   Remover dependencias em string.h e ctype.h
 */
#include <string.h>
#include <ctype.h>

/*
 * A função devolve o n-ésimo caracter da string, ou o caracter nulo
 * ‘\0’ caso o tamanho da string seja menor que o número inteiro indicado.
 */
char n_esimo( char *string, int loc )
{
    if (loc > strlen(string)) {
        return '\0';
    } else {
        return string[loc - 1];
    }
}

/*
 * A função strduplica() recebe uma string e duplica o seu conteúdo.
 */
char* strduplica(char *String)
{
    char temp[50]; // string temporaria
    strcpy(temp,String);

    return strcat(String,temp);
}

/*
 * A função mystrlen() tem como parâmetro de entrada uma string e
 * deve devolver o número de caracteres da string.
 * @TODO:   Implementação errada, devolve o número de caracteres
 *          excluindo espaços.
 */
int mystrlen(const char *String)
{
    int i, loc=0;

    for( i=0; String[i] != '\0'; i++)
    {
        if(!isspace(String[i]))
            loc = i+1;
    }
    return loc;
}

/*
 * A função mystrcpy() tem como parâmetros de entrada uma string
 * de destino e uma string fonte. Copia o conteúdo de uma para outra.
 */
char * mystrcpy(char *Destino, char *Fonte)
{
    int i;

    for( i=0; Fonte[i] != '\0'; i++)
    {
        Destino[i]=Fonte[i];
    }
    Destino[i]='\0';

    return Destino;
}

/*
 * A função mystrcat() tem como parâmetros de entrada uma string
 * de destino e uma string fonte. Coloca a string fonte a seguir ao final da string
 * destino. Note-se que não é colocado qualquer caracter a separar as duas strings.
 */
char * mystrcat(char *Destino, const char *Fonte)
{
    int i;
    int size = strlen(Destino);

    for( i = 0; Fonte[i]!='\0';i++)
    {
        Destino[size+i]=Fonte[i];
    }

    Destino[size+i]='\0';

    return Destino;
}

/*
 * A função mystrcountc() que deve ter como parâmetros de entrada uma
 * string, e um caracter. Devolve o número de ocorrências do caracter na string.
 */
int mystrcountc(const char *s, char car)
{
    int counter=0, i;

    for( i = 0; s[i]!='\0';i++)
    {
        if(s[i]==car)
            counter++;
    }

    return counter;
}

/*
 * A função mystrrev() tem como parâmetros de entrada uma string.
 * Devolve a string invertida.
 */
char * mystrrev(char *s)
{
    char temp;
    int i;
    unsigned int size = strlen(s)-1;

    for( i = 0; i < (size/2); i++ )
    {
        temp = s[i];
        s[i] = s[size-i];
        s[size-i] = temp;
    }
    return s;
}

/*
 * A função mystrpack que deve ter como parâmetros de entrada uma string.
 * Devolve a string em que qualquer conjunto de caracteres repetidos consecutivos
 * são compactados num único caracter.
 * Primeira versão usando memset()
 */
char * mystrpack(char *s)
{
    int i,j=1;
    char temp[50]; // iniciar array string

    memset(temp, '\0', sizeof(temp)); // criar/popular string
    temp[0] = s[0];

    for( i=1; s[i] != '\0'; i++) {
        if (s[i-1] == s[i]) // se car actual for igual ao car da pos anterior saltar iter
            continue;
        temp[j] = s[i]; // copiar car n repetido para nova string
        j++;
    }

    temp[j]='\0';

    return strcpy(s, temp); // copiar string temp
}

/*
 * A função mystrpack que deve ter como parâmetros de entrada uma string.
 * Devolver a string em que qualquer conjunto de caracteres repetidos consecutivos
 * são compactados num único caracter.
 * Segunda versão sem necessitar string temp.
 */
char * mystrpack_while(char *String)
{
    int numPos = 0, numActual = 0;

    while (String[numActual]) // enquanto não se encontra '\0'
    {
        if (String[numActual-1] != String[numActual]) // Se posicao actual não for um car repetido
        {
            String[numPos] = String[numActual]; // copiar car para ultima pos nao repetida
            numPos++; // avancar uma pos nao repetida
        }
        numActual++; // avancar pos actual
    }

    String[numPos]='\0';

    return String;
}

/*
 * A função inimai() tem como parâmetros de entrada uma string.
 * Devolve a string em que as várias palavras contidas começam por maiúsculas.
 */
char * inimai(char *s)
{
    int i=1;

    /*
     * Primeira letra será sempre capitalizada.
     * Não tem em conta ser possivel que input comece
     * nao por uma palavra.
     * @TODO: adicionar input check
     */
    s[0]= toupper(s[0]);

    while( s[i] ) {
        if (s[i-1] != ' ')
        {
            s[i] =tolower(s[i]);
        } else {
            s[i]= toupper(s[i]);
        }
        i++;
    }

    return s;
}

/*
 * A função myatoi() recebe uma string e devolve o inteiro nela representado.
 * @TODO:   De momento não considera "+" como valor inicial.
 *          Futuramente procurar primeira instancia de um valor numerico.
 */
int myatoi(char *s)
{
    int i=0, num=0, negativo=0;

    /*
     * Se primeiro char for -, valor negativo
     * Começar a procurar numero em s[1]
     */
    if (s[0] == '-')
        i = negativo = 1;


    while (isdigit(s[i])){
        num *= 10;           // Novo numero encontrado, multiplicar por 10
        num += s[i] - '0';   // Converter char para int. ASCII numero - valor ASCII de '0'
        i++;                 // Andar indice +1
    }

    if (negativo)
        num *= -1; // Tornar $num em valor negativo

    return num;
}

/*
 * A função strcount() tem como parâmetros de entrada uma string.
 * Devolve o número de palavras contidas na string.
 */
int strcount(char *s)
{
    int i=0, counter=0, found=0;

    while( s[i] ) {
        /*
        *  Se posicao actual nao for um espaco e uma palavra
        *  ter ja sido encontrada, avancar prox pos
        */
        if(s[i] != ' ' && found == 1)
        {
            i++;
        /*
         * Se posicao actual nao for um espaco e nenhuma
         * palavra tiver sido encontrada, sinalizar encontrada
         * Avancar counter e pos
         */
        } else if ( s[i] != ' ' && found == 0) {
            found = 1;
            counter++;
            i++;
            /*
             * Espaco encontrado, avancar pos
             */
        } else {
            found = 0;
            i++;
        }

    }

    return counter;
}

/*
 * A função strsubst() tem como parâmetros de entrada uma string e
 * dois caracteres. Devolve uma string em que, se o primeiro caracter for encontrado, é
 * substituído pelo segundo.
 */
char * strsubst(char *s, char oldCar, char newCar)
{
    int i=0;

    while (s[i])
    {
        if (s[i] == oldCar )
        {
            s[i] = newCar;
        }
        i++;
    }

    return s;
}