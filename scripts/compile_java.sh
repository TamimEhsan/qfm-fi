
# Run my one
# Compile the java files

# Original one
javac -cp ../lib/* -d ../qfm_fi/bin ../qfm_fi/src/qfm_fi/*.java

# Modified One
javac -cp ../lib/* -d ../qfm_fi_modified/bin ../qfm_fi_modified/src/qfm_fi_mod/*.java


data_root=/mnt/g/Research/Dataset_Simulated/37-taxon/
folder_names=('scale2d.200g.500b' 'noscale.400g.500b' 'noscale.200g.500b' 'noscale.200g.1000b'
'noscale.800g.500b' 'noscale.25g.500b' 'noscale.200g.250b'   'noscale.100g.500b'
'scale2u.200g.500b' 'noscale.50g.500b' 'noscale.200g.true'  'noscale.200g.1500b')


for (( iter = 0; iter < ${#folder_names[*]}; iter++ ));
do
    folderName=${folder_names[$iter]}
    for (( i = 1; i <= 20; i++ ));
    do
        data_file=$data_root$folderName/R$i/weighted_quartets
        echo $data_file
        # run the project
        # Original One
        java -cp ../qfm_fi/bin:../lib/* qfm_fi.Main $data_file /mnt/g/Research/QFM_FI_LATEST/qfm_java/IO/output_org.txt >/dev/null

        # Modified One
        java -cp ../qfm_fi_modified/bin:../lib/* qfm_fi_mod.Main $data_file /mnt/g/Research/QFM_FI_LATEST/qfm_java/IO/output_mod.txt >/dev/null

        # Get the scores
        # Original One
        org_score=$(python /mnt/g/Research/RF_Score/rfScoreCalculator/getFpFn.py -t /mnt/g/Research/Dataset_Simulated/37-taxon/True.tree -e /mnt/g/Research/QFM_FI_LATEST/qfm_java/IO/output_org.txt)

        # Modified One
        mod_score=$(python /mnt/g/Research/RF_Score/rfScoreCalculator/getFpFn.py -t /mnt/g/Research/Dataset_Simulated/37-taxon/True.tree -e /mnt/g/Research/QFM_FI_LATEST/qfm_java/IO/output_mod.txt)

        if [[ "$org_score" != "$mod_score" ]]
        then
            echo "$org_score,$mod_score,$folderName,R$i," >> /mnt/g/Research/QFM_FI_LATEST/qfm_java/IO/diff.txt
        fi
    done
done
