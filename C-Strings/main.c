#include <stdio.h>
#include "newStrings.h"

int main() {
    char src[40];
    char dest[100];

    memset(dest, '\0', sizeof(dest));
    mystrcpy(src, "This is tutorialspoint.com");
    mystrcpy(dest, src);

    printf("Final copied string : %s\n", dest);

    return 0;
}

