package com.akdndhrc.hayat.lhs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import com.akdndhrc.hayat.lhs.Adapter.Validation_Extandable_List;
import com.akdndhrc.hayat.lhs.Checklist.LHS_Checklist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Validation extends AppCompatActivity {
    String activity_id,activity_month;
    Validation_Extandable_List listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    List<String> listCount;
    HashMap<String, List<String>> listDataChild;
    String basicinfo[][], childhealth[][], maternalhealth[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);


        // setting list adapter


        // preparing list data
        activity_id=getIntent().getExtras().getString("activity_id");
        activity_month=getIntent().getExtras().getString("activity_month");

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        prepareListData();

        listAdapter = new Validation_Extandable_List(this, listDataHeader, listDataChild);

        expListView.setAdapter(listAdapter);

    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {


        try{
            Lister ls= new Lister(Validation.this);
            ls.createAndOpenDB();

            String res= "Select household_registered_lhw, tap, spring, handpump, well, other," +
                    " age_1223_count, age_1223_fully_imunized, age_lt3_count, age_lt3_gm, age_lt3_malnurished, new_borns_1week," +
                    " low_birth_weight, breast_fed, immunized,new_preg,total_preg,total_vistis,iron_sup,abortions,delivey_4p,delivery_pnc," +
                    " delivery_immunized,eligible,provided, followup,modern,condom_users,pill_users,injectible_users,iucd_users,surgical_users," +
                    " other_users,traditional_users,referred,supplied_condoms,supplied_pills,supplied_injectibles,diarrhea_a5,diarrhea_u5," +
                    " ari_a5,ari_u5,fever_a5,fever_u5,resp_a5,resp_u5,anaemia_a5,anaemia_u5,scabies_a5,scabies_u5,eye_infections_a5,eye_infections_u5," +
                    " rtis_a5,rtis_u5,worm_a5,worm_u5,malaria_a5,malaria_u5,referral_a5,referral_u5,tb_suspect_a5,tb_suspect_u5,tb_diagnosed_a5," +
                    " tb_diagnosed_u5,tb_followed_a5,tb_followed_u5,live,still,deaths_all,noenatal,infant,children,maternal,tab_paracetamol," +
                    " syp_paracetamol,tab_choloroquin,syp_choloroquin,tab_mebendazole,syp_pipearzine,ors,eye_ontiment,syp_contrimexazole,iron_tab," +
                    " antiseptic_lotion,benzyle_benzoate_lotion,sticking_plaster,b_complex_syp,cotton_bandages,cotton_wool,condoms,oral_pills," +
                    " contraceptive_inj,med_others,lhw_kit_bag,weighing_machine,thermometer,torch_with_cell,scissors,syringe_cutter,mis_others," +
                    " lhs,dco,adc,fpo,ppiu, max(added_on) from MONTHLY_REPORT where month = '"+activity_month+"'";
            //basicinfo= ls.executeReader("Select household_registered_lhw, tap, spring, handpump, well, other from MONTHLY_REPORT ");
            childhealth= ls.executeReader(res);

            Log.d("000999", "prepareListData: "+res);

            for (int i = 0; i<childhealth.length; i++){
                Log.d("000999", "prepareListData: "+childhealth[i][0]);
            }
            Log.d("000999", "prepareListData: "+childhealth[0][0]);


        }
        catch (Exception e){
            Log.d("000999", "prepareListData: "+e);
        }


        // Adding header data
        listDataHeader.add("Basic information");
        listDataHeader.add("Social contacts");
        listDataHeader.add("Child health");
        listDataHeader.add("Mother health");
        listDataHeader.add("Family planning");
        listDataHeader.add("Common ailments of patients above 5 years");
        listDataHeader.add("Common ailments of patients under 5 years");
        listDataHeader.add("Births/deaths");
        listDataHeader.add("Logistic (LHW kit)");
        listDataHeader.add("Miscellanous");
        listDataHeader.add("Supervision");

        try {
            // Adding child data
            List<String> basicInformation = new ArrayList<String>();
            basicInformation.add("No of formulated health commettees" + "@" + "0");
            basicInformation.add("No of formulated Women support groups" + "@" + "1");
            basicInformation.add("Household registered by LHWs" + "@" + childhealth[0][0]);
            basicInformation.add("Household with source of drinking water, tap" + "@" + childhealth[0][1]);
            basicInformation.add("Household with source of drinking water, spring" + "@" + childhealth[0][2]);
            basicInformation.add("Household with source of drinking water, handpump" + "@" + childhealth[0][3]);
            basicInformation.add("Household with source of drinking water, other" + "@" + childhealth[0][4]);
            basicInformation.add("Household with source of drinking water, other" + "@" + childhealth[0][5]);
            basicInformation.add("Household having flush system with sewerage/septic tank" + "@" + "3");

            List<String> socialContacts = new ArrayList<String>();
            socialContacts.add("No of health committee meetings" + "@" + "0");
            socialContacts.add("No of women support group meetings" + "@" + "0");
            socialContacts.add("No of health education sessions held in school" + "@" + "0");

            List<String> childHealth = new ArrayList<String>();
            childHealth.add("No of new borns weighted (with in One week after birth)" + "@" + childhealth[0][11]);
            childHealth.add("No of low birth weight babies" + "@" + childhealth[0][12]);
            childHealth.add("No of new born started breast feeding within 24 hours" + "@" + childhealth[0][13]);
            childHealth.add("No of infants whose immunization has started this month" + "@" + childhealth[0][14]);
            childHealth.add("No of 12-23 months old children" + "@" + childhealth[0][6]);
            childHealth.add("No of 12-23 months old children fully immunized" + "@" + childhealth[0][7]);
            childHealth.add("No of <3 years children" + "@" + childhealth[0][8]);
            childHealth.add("No of <3 years children whose GM done & recorded" + "@" + childhealth[0][9]);
            childHealth.add("No Of <3 years children malnourished" + "@" + childhealth[0][10]);


            List<String> maternal_health = new ArrayList<String>();
            maternal_health.add("No. of newly registered pregnant women" + "@" + childhealth[0][15]);
            maternal_health.add("Total pregnant women (new + follow up)" + "@" + childhealth[0][16]);
            maternal_health.add("Total pregnant women visited (new + followup)" + "@" + childhealth[0][17]);
            maternal_health.add("No. of pregnant women supplied iron tablets" + "@" + childhealth[0][18]);
            maternal_health.add("No. of abortions (pregnancy less than 7 Months)" + "@" + childhealth[0][19]);
            maternal_health.add("No of women delivered having 4 or >4 ANC visits by SBAs (Dr, Nurse, LHV, Midwife/CMW)" + "@" + childhealth[0][20]);
            maternal_health.add("No of women delivered With TT completed before delivery" + "@" + childhealth[0][21]);
            maternal_health.add("No of deliveries by skilled birth attendants (Dr, Nurse, LHV, Midwife/CMW)" + "@" + childhealth[0][22]);
            maternal_health.add("No. of post-natal cases visited within 24 hours" + "@" + "0");

            List<String> family_planning = new ArrayList<String>();
            family_planning.add("No of eligible couples (Age of wife between 15-49 years)" + "@" + childhealth[0][23]);
            family_planning.add("No of new client of family planning (modern + traditional)" + "@" + childhealth[0][24]);
            family_planning.add("No of follow-up cases for family planning (modern + traditional)" + "@" + childhealth[0][25]);
            family_planning.add("Total no of modern contraceptive method users" + "@" + childhealth[0][26]);
            family_planning.add("No of condom users" + "@" + childhealth[0][27]);
            family_planning.add("No of oral pills users" + "@" + childhealth[0][28]);
            family_planning.add("No of injectable contraceptive users" + "@" + childhealth[0][29]);
            family_planning.add("No of IUCD client" + "@" + childhealth[0][30]);
            family_planning.add("No of surgical clients" + "@" + childhealth[0][31]);
            family_planning.add("No of other modern contraceptive method users" + "@" + childhealth[0][32]);
            family_planning.add("Total no of traditional method users" + "@" + childhealth[0][33]);
            family_planning.add("No of family planning clients referred" + "@" + childhealth[0][34]);
            family_planning.add("No of clients supplied condoms" + "@" + childhealth[0][35]);
            family_planning.add("No of clients supplied oral pills" + "@" + childhealth[0][36]);
            family_planning.add("No of clients administered injectable contraceptives" + "@" + childhealth[0][37]);

            List<String> common_aliments_a5 = new ArrayList<String>();
            common_aliments_a5.add("No of cases of diarrhoea" + "@" + childhealth[0][38]);
            common_aliments_a5.add("No of cases of ARI" + "@" + childhealth[0][40]);
            common_aliments_a5.add("No of cases of fever" + "@" + childhealth[0][42]);
            common_aliments_a5.add("No of cases of injuries/burns" + "@" + childhealth[0][44]);
            common_aliments_a5.add("No of cases of anaemia" + "@" + childhealth[0][46]);
            common_aliments_a5.add("No of cases of scabies" + "@" + childhealth[0][48]);
            common_aliments_a5.add("No of cases of eye infections" + "@" + childhealth[0][50]);
            common_aliments_a5.add("No of female cases of reproductive tract infections" + "@" + childhealth[0][52]);
            common_aliments_a5.add("No of cases of worm infestation" + "@" + childhealth[0][54]);
            common_aliments_a5.add("No of cases of malaria" + "@" + childhealth[0][56]);
            common_aliments_a5.add("No of referral cases" + "@" + childhealth[0][58]);
            common_aliments_a5.add("No of of Suspected cases of TB referred" + "@" + childhealth[0][60]);
            common_aliments_a5.add("No of diagnosed cases of TB" + "@" + childhealth[0][62]);
            common_aliments_a5.add("No of TB patients followed by LHW (as T/M support)" + "@" + childhealth[0][64]);

            List<String> common_aliments_u5 = new ArrayList<String>();
            common_aliments_u5.add("No of cases of diarrhoea" + "@" + childhealth[0][39]);
            common_aliments_u5.add("No of cases of ARI" + "@" + childhealth[0][41]);
            common_aliments_u5.add("No of cases of fever" + "@" + childhealth[0][43]);
            common_aliments_u5.add("No of cases of injuries/burns" + "@" + childhealth[0][45]);
            common_aliments_u5.add("No of cases of anaemia" + "@" + childhealth[0][47]);
            common_aliments_u5.add("No of cases of scabies" + "@" + childhealth[0][49]);
            common_aliments_u5.add("No of cases of eye infections" + "@" + childhealth[0][51]);
            common_aliments_u5.add("No of female cases of reproductive tract infections" + "@" + childhealth[0][53]);
            common_aliments_u5.add("No of cases of worm infestation" + "@" + childhealth[0][55]);
            common_aliments_u5.add("No of cases of malaria" + "@" + childhealth[0][57]);
            common_aliments_u5.add("No of referral cases" + "@" + childhealth[0][59]);
            common_aliments_u5.add("No of of suspected cases of TB referred" + "@" + childhealth[0][61]);
            common_aliments_u5.add("No of diagnosed cases of TB" + "@" + childhealth[0][63]);
            common_aliments_u5.add("No of TB patients followed by LHW (as T/M support)" + "@" + childhealth[0][65]);

            List<String> births_death = new ArrayList<String>();
            births_death.add("No of live births" + "@" + childhealth[0][66]);
            births_death.add("No of still births (Pregnancy more than 07 months)" + "@" + childhealth[0][67]);
            births_death.add("No of all deaths" + "@" + childhealth[0][68]);
            births_death.add("No of neo-natal deaths (Within 1 week of birth)" + "@" + childhealth[0][69]);
            births_death.add("No of infant deaths (Age more than 1 week but less than 1 year)" + "@" + childhealth[0][70]);
            births_death.add("No of children deaths (Age more than 1 year but less than 05 years)" + "@" + childhealth[0][71]);
            births_death.add("No of maternal deaths" + "@" + childhealth[0][72]);

            List<String> logistics = new ArrayList<String>();
            logistics.add("Tab. Paracetamol" + "@" + childhealth[0][73]);
            logistics.add("Syp. Paracetamol" + "@" + childhealth[0][74]);
            logistics.add("Tab. Choloroquin" + "@" + childhealth[0][75]);
            logistics.add("Syp. Choloroquin" + "@" + childhealth[0][76]);
            logistics.add("Tab. Mebendazole" + "@" + childhealth[0][77]);
            logistics.add("Syp. Pipearzine" + "@" + childhealth[0][78]);
            logistics.add("ORS" + "@" + childhealth[0][79]);
            logistics.add("Eye Ontiment" + "@" + childhealth[0][80]);
            logistics.add("Syp. Contrimexazole" + "@" + childhealth[0][81]);
            logistics.add("Iron Tab." + "@" + childhealth[0][82]);
            logistics.add("Antiseptic Lotion" + "@" + childhealth[0][83]);
            logistics.add("Benzyle Benzoate Lotion" + "@" + childhealth[0][84]);
            logistics.add("Sticking Plaster" + "@" + childhealth[0][85]);
            logistics.add("B. Complex Syp" + "@" + childhealth[0][86]);
            logistics.add("Cotton Bandages" + "@" + childhealth[0][87]);
            logistics.add("Cotton Wool" + "@" + childhealth[0][88]);
            logistics.add("Condoms" + "@" + childhealth[0][89]);
            logistics.add("Oral Pills" + "@" + childhealth[0][90]);
            logistics.add("Contraceptive Inj." + "@" + childhealth[0][91]);
            logistics.add("Others" + "@" + childhealth[0][92]);

            List<String> miscellanous = new ArrayList<String>();
            miscellanous.add("LHW kit bag" + "@" + childhealth[0][93]);
            miscellanous.add("Weighing machine" + "@" + childhealth[0][94]);
            miscellanous.add("Thermometer" + "@" + childhealth[0][95]);
            miscellanous.add("Torch with cell" + "@" + childhealth[0][96]);
            miscellanous.add("Scissors" + "@" + childhealth[0][97]);
            miscellanous.add("Syringe cuttur" + "@" + childhealth[0][98]);
            miscellanous.add("Others" + "@" + childhealth[0][99]);

            List<String> supervision = new ArrayList<String>();
            supervision.add("No of visits by LHS" + "@" + childhealth[0][100]);
            supervision.add("No of visit by DCO (NP)" + "@" + childhealth[0][101]);
            supervision.add("No of visit by ADC (NP)" + "@" + childhealth[0][102]);
            supervision.add("No of visit by FPO" + "@" + childhealth[0][103]);
            supervision.add("No of visit by PPIU" + "@" + childhealth[0][104]);

            listDataChild.put(listDataHeader.get(0), basicInformation); // Header, Child data
            listDataChild.put(listDataHeader.get(1), socialContacts);
            listDataChild.put(listDataHeader.get(2), childHealth);
            listDataChild.put(listDataHeader.get(3), maternal_health); // Header, Child data
            listDataChild.put(listDataHeader.get(4), family_planning);
            listDataChild.put(listDataHeader.get(5), common_aliments_a5);
            listDataChild.put(listDataHeader.get(6), common_aliments_u5);
            listDataChild.put(listDataHeader.get(7), births_death); // Header, Child data
            listDataChild.put(listDataHeader.get(8), logistics);
            listDataChild.put(listDataHeader.get(9), miscellanous);
            listDataChild.put(listDataHeader.get(10), supervision);
        }
        catch(Exception e){
            Log.d("000999", "prepareListData: "+e);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(Validation.this, Checklist_Validation_Button.class);
        intent.putExtra("activity_id",activity_id);
        intent.putExtra("activity_month",activity_month);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

