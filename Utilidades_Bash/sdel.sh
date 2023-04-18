#!/bin/bash

# Define as variáveis globais
LIXO="$HOME/.LIXO"
LOG="$HOME/.sdel.log"
TAR_OPCOES="-czvf"

# Função para imprimir a mensagem de ajuda
ajuda() {
    echo -e "\e[1mUso: sdel [ficheiro ...] [-r dir] [-t num] [-s num] [-u] [-h]\e[0m"
    echo "Apaga ficheiros em segurança."
    echo ""
    echo -e "\e[1mOpções disponiveis:\e[0m"
    echo "  -r dir      Remove o diretorio recursivamente"
    echo "  -t horas    Remove todos os arquivos com mais de -t horas na diretoria lixo"
    echo "  -s kbytes   Remove os arquivos maiores de -s kilobytes na diretoria lixo"
    echo "  -u          Indica o tamanho do maior ficheiro guardado na diretoria lixo"
    echo "  -h          Mostra esta mensagem de ajuda"
    echo ""
    if [ $# -gt 0 ]; then
        echo -e "\e[1mExemplos:\e[0m"
        echo ""
        echo -e "\e[1m\e[3msdel -r dir1\e[0m\e[0m"
        echo -e "> Resultado: O comando sdel é aplicado recursivamente na diretoria \e[1mdir1\e[0m."
        echo ""
        echo -e "\e[1m\e[3msdel -t 48\e[0m\e[0m"
        echo -e "> Os ficheiros da diretoria ~/.LIXO com mais de \e[1m48\e[0m horas são apagados."
        echo ""
        echo -e "\e[1m\e[3msdel -s 100\e[0m\e[0m"
        echo -e "> Resultado: Os ficheiros da diretoria ~/.LIXO com mais de \e[1m100\e[0m KBytes são apagados."
        echo ""
    fi
}

# Se directorio /.LIXO não existir, criar
if [ ! -d "$LIXO" ]; then
  mkdir "$LIXO"
fi

# Função para comprimir o arquivo usando o tar
comp_ficheiro() {
    local timestamp=$(date +%N | cut -b1-5) # Obter nanosegundos da op e "cortar" primeiros 5 digitos
    local ficheiro_tipo="$1"
    shift  

    # Obsoleto: Verificado antes, deixo just in case se me esqueci de algo
    if [[ $# -eq 0 ]]; then
        echo "Erro: pelo menos um arquivo precisa ser especificado."
        return 1
    fi

    if [ "$ficheiro_tipo" = true ]; then
        local prim_param=$(echo "$@" | cut -d ' ' -f 1)
        # Para evitar que ao comprimir/mover ficheiros de nomes iguais
        # Se sobreponham na pasta ~/.LIXO, adicionar data e timestamp unico
        local saida="${prim_param}-$(date +%d%m%y)-${timestamp}.tar.gz"

        if tar $TAR_OPCOES "$saida" $@ && rm $@; then
            echo "Compressão bem sucedida e ficheiro(s) movido(s) para diretoria lixo com sucesso."
            log_operacao "" "Arquivos comprimidos com sucesso em $saida" true
        else
            echo "Erro: Não foi possivel comprimir ou remover ficheiros!"
            log_operacao "$saida" "Não foi possivel comprimir ou remover ficheiro!" false
        fi
    else
        local diretoria="$@"
        local diretoria="${diretoria%/}" #Remover / do argumento passado pois estava a criar erro com o tar
        local saida="${diretoria}-$(date +%d%m%y)-${timestamp}.tar.gz"

        if tar $TAR_OPCOES "$saida" "$diretoria" && rm -Rv "$diretoria"; then
            echo "Compressão bem sucedida e diretoria movida para diretoria lixo com sucesso."
            log_operacao "" "Arquivos comprimidos com sucesso em $saida" true
        else
            echo "Erro: Não foi possivel comprimir ou mover diretoria!"
            log_operacao "$saida" "Não foi possivel comprimir ou remover ficheiro!" false
        fi
    fi
    
    mover_lixeira "$saida"
}

# Função para mover o arquivo para a diretoria lixo
mover_lixeira() {
    ficheiro=$1
    if mv "$ficheiro" "$LIXO"; then
        log_operacao "$ficheiro" "Arquivo movido para a diretoria lixo com sucesso." true
    else
        log_operacao "$ficheiro" "Não foi possivel mover ficheiro para a diretoria lixo!" false
    fi
}

# Função para remover arquivo ou diretório
remover_dir() {
    dir=$1
    if [ -d "$dir" ]; then
        comp_ficheiro false "$dir"
        log_operacao "" "Diretório ${dir} movido para a diretoria lixo com sucesso" true
    else
        echo "Erro: $dir não é uma diretoria válida!"
        log_operacao "$dir" "$dir não é uma diretoria válida!" false
        exit 1
    fi
}

# Função para remover arquivos mais antigos que um certo número de horas
remove_idade() {
    num_horas=$1
    find "$LIXO" -type f -mmin "+$((num_horas * 60))" -delete
    log_operacao "" "Arquivos com mais de ${num_horas} horas removidos da diretoria lixo." true
}

# Função para remover arquivos maiores que um certo tamanho em Kilobytes
remove_tamanho() {
    tamanho=$1
    find "$LIXO" -type f -size "+${tamanho}k" -delete
    log_operacao "" "Arquivos maiores que ${tamanho} Kilobytes removidos da diretoria lixo." true
}

# Função para retornar o maior arquivo da diretoria de lixo
maior_ficheiro() {
    # grep "^-" : seleciona apenas ficheiros normais
    # awk '{print $NF}' : seleciona apenas o nome do ficheiro, ultima coluna do ls
    maior_ficheiro=$(ls -lS "$LIXO" | grep "^-" | head -1 | awk '{print $NF}')
    if [ -n "$maior_ficheiro" ]; then
        echo "O maior arquivo na diretoria lixo é: $maior_ficheiro"
    else
        echo "A diretoria lixo encontra-se vazia!"
    fi
}

# Função para criar log da operacao
log_operacao() {
  local ficheiro="$1"
  local msg="$2"
  local sucesso="$3"
  
  if $sucesso; then
    if [ -z "$ficheiro" ]; then
        echo "$(date '+%Y-%m-%d %H:%M:%S'): $USER: $msg" >> "$LOG"
    else
        echo "$(date '+%Y-%m-%d %H:%M:%S'): $USER: $ficheiro -> $LIXO/$ficheiro" >> "$LOG"
    fi
  else
    echo "$(date '+%Y-%m-%d %H:%M:%S'): $USER: ERRO->$msg" >> "$LOG"
  fi
}

# Verifica se o número de argumentos é válido
if [ "$#" -eq 0 ]; then
  ajuda
  exit 1
fi

# Verifica se a opção é válida
if [[ "$1" != -* ]]; then # verifica se opção começa por -
    # Passa todos os ficheiros como um unico argumento
    files=()
    for arg in "$@"; do
        if [[ -f "$arg" ]]; then # -f verifica se é um ficheiro "regular"
            files+=("$arg")
        elif [[ -d "$arg" ]]; then # Se é uma diretoria, ignorar
            echo "$arg é uma diretoria, ignorando."
            log_operacao "" "$arg é uma diretoria, ignorada para a operacao." false
        else
            echo "Erro: $arg não existe ou não é válido!"
            log_operacao "$arg" "Ficheiro não existe ou não é válido!" false
            exit 1
        fi
    done

    # Verifica se pelo menos um ficheiro foi encontrado
    if [[ ${#files[@]} -eq 0 ]]; then
        echo "Erro: pelo menos um arquivo precisa ser especificado."
        log_operacao "" "Pelo menos um arquivo precisa ser especificado." false
    else
        # Passa todos os ficheiros como um unico argumento
        comp_ficheiro true "${files[*]}"
    fi
else
    # Processa as opções
    while getopts ":r:t:s:uh" opt; do
        case $opt in
            r)
                remover_dir "$OPTARG"
                ;;
            t)
                remove_idade "$OPTARG"
                ;;
            s)
                remove_tamanho "$OPTARG"
                ;;
            u)
                maior_ficheiro
                ;;
            h)
                ajuda true
                exit 0
                ;;
            \?)
                echo "Opção inválida: -$OPTARG"
                exit 1
                ;;
            :)
                echo "Opção -$OPTARG requer um argumento"
                exit 1
                ;;
        esac
    done
fi

exit 0